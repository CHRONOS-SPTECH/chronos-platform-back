package chronos.tech.application.service;

import chronos.tech.application.dto.request.AuthLoginRequestDTO;
import chronos.tech.application.dto.request.AuthRegisterRequestDTO;
import chronos.tech.application.dto.response.AuthResponseDTO;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.mapper.UsuarioMapper;
import chronos.tech.application.port.in.AuthUseCase;
import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.Usuario;
import chronos.tech.domain.model.classes.UsuarioPerfil;
import chronos.tech.domain.model.classes.compostas.UsuarioPerfilId;
import chronos.tech.domain.port.PerfilAcessoRepository;
import chronos.tech.domain.port.UsuarioRepository;
import chronos.tech.domain.port.UsuarioPerfilRepository;
import chronos.tech.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PerfilAcessoRepository perfilAcessoRepository;
    private final PessoaMapper pessoaMapper;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioPerfilRepository usuarioPerfilRepository;

    @Override
    @Transactional // Adicionado para garantir que Pessoa, Usuario e Perfis sejam salvos ou falhem juntos
    public AuthResponseDTO register(AuthRegisterRequestDTO dto) {
        // 1. Verificação de existência
        usuarioRepository.findByEmailLogin(dto.email_login()).ifPresent(u -> {
            throw new RuntimeException("E-mail já cadastrado");
        });

        // 2. Mapeamento e persistência do Usuário (Cascade deve cuidar da Pessoa se configurado)
        Pessoa pessoa = pessoaMapper.toModel(dto.pessoa());
        Usuario usuario = new Usuario();
        usuario.setPessoa(pessoa);
        usuario.setEmailLogin(dto.email_login());
        usuario.setSenhaHash(passwordEncoder.encode(dto.senha()));
        usuario.setStatusAtivo(Boolean.TRUE);
        usuario.setDataCriacao(LocalDateTime.now());

        // Salva o usuário primeiro para gerar o ID
        Usuario salvo = usuarioRepository.save(usuario);

        // 3. Persistência da relação N:N (UsuarioPerfil)
        if (dto.perfis_id() != null && !dto.perfis_id().isEmpty()) {
            for (Long perfilId : dto.perfis_id()) {
                PerfilAcesso perfil = perfilAcessoRepository.findById(perfilId)
                        .orElseThrow(() -> new RuntimeException("Perfil de acesso não encontrado: " + perfilId));

                // CORREÇÃO CRÍTICA: Instanciar a chave composta
                UsuarioPerfilId idComposta = new UsuarioPerfilId();

                // Setando os IDs na chave (Ajuste o cast para .intValue() se o seu ID no modelo for Integer)
                idComposta.setIdUsuario(salvo.getIdUsuario());
                idComposta.setIdPerfil(perfil.getIdPerfil().intValue());

                UsuarioPerfil vinculo = new UsuarioPerfil();
                vinculo.setId(idComposta); // Atribui a chave composta instanciada
                vinculo.setUsuario(salvo);
                vinculo.setPerfil(perfil);

                usuarioPerfilRepository.save(vinculo);
            }
        }

        String token = jwtService.generateToken(salvo.getEmailLogin());
        return new AuthResponseDTO(token, "Bearer", usuarioMapper.toResponse(salvo));
    }

    @Override
    public AuthResponseDTO login(AuthLoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email_login(), dto.senha())
        );
        Usuario usuario = usuarioRepository.findByEmailLogin(dto.email_login())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.generateToken(usuario.getEmailLogin());
        return new AuthResponseDTO(token, "Bearer", usuarioMapper.toResponse(usuario));
    }
}