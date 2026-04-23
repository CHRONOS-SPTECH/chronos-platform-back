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
import chronos.tech.domain.port.PerfilAcessoRepository;
import chronos.tech.domain.port.UsuarioRepository;
import chronos.tech.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public AuthResponseDTO register(AuthRegisterRequestDTO dto) {
        usuarioRepository.findByEmailLogin(dto.email_login()).ifPresent(u -> {
            throw new RuntimeException("E-mail ja cadastrado");
        });

        Pessoa pessoa = pessoaMapper.toModel(dto.pessoa());
        PerfilAcesso perfil = null;
        if (dto.perfil_id() != null) {
            perfil = perfilAcessoRepository.findById(dto.perfil_id())
                    .orElseThrow(() -> new RuntimeException("Perfil de acesso nao encontrado: " + dto.perfil_id()));
        }

        Usuario usuario = new Usuario();
        usuario.setPessoa(pessoa);
        usuario.setPerfil(perfil);
        usuario.setEmail_login(dto.email_login());
        usuario.setSenha_hash(passwordEncoder.encode(dto.senha()));
        usuario.setStatus_ativo(Boolean.TRUE);
        usuario.setData_criacao(LocalDateTime.now());

        Usuario salvo = usuarioRepository.save(usuario);
        String token = jwtService.generateToken(salvo.getEmail_login());
        return new AuthResponseDTO(token, "Bearer", usuarioMapper.toResponse(salvo));
    }

    @Override
    public AuthResponseDTO login(AuthLoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email_login(), dto.senha())
        );
        Usuario usuario = usuarioRepository.findByEmailLogin(dto.email_login())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        String token = jwtService.generateToken(usuario.getEmail_login());
        return new AuthResponseDTO(token, "Bearer", usuarioMapper.toResponse(usuario));
    }
}
