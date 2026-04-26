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
import chronos.tech.domain.port.PerfilAcessoRepository;
import chronos.tech.domain.port.UsuarioRepository;
import chronos.tech.domain.port.UsuarioPerfilRepository;
import chronos.tech.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    public AuthResponseDTO register(AuthRegisterRequestDTO dto) {
        usuarioRepository.findByEmailLogin(dto.email_login()).ifPresent(u -> {
            throw new RuntimeException("E-mail ja cadastrado");
        });

        Pessoa pessoa = pessoaMapper.toModel(dto.pessoa());
        Usuario usuario = new Usuario();
        usuario.setPessoa(pessoa);
        usuario.setEmailLogin(dto.email_login());
        usuario.setSenhaHash(passwordEncoder.encode(dto.senha()));
        usuario.setStatusAtivo(Boolean.TRUE);
        usuario.setDataCriacao(LocalDate.now());

        Usuario salvo = usuarioRepository.save(usuario);

        if (dto.perfis_id() != null) {
            for (Long perfilId : dto.perfis_id()) {
                PerfilAcesso perfil = perfilAcessoRepository.findById(perfilId)
                        .orElseThrow(() -> new RuntimeException("Perfil de acesso nao encontrado: " + perfilId));
                UsuarioPerfil vinculo = new UsuarioPerfil();
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
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        String token = jwtService.generateToken(usuario.getEmailLogin());
        return new AuthResponseDTO(token, "Bearer", usuarioMapper.toResponse(usuario));
    }
}
