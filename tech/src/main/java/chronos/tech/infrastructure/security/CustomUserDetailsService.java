package chronos.tech.infrastructure.security;

import chronos.tech.domain.port.UsuarioRepository;
import chronos.tech.domain.port.UsuarioPerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioPerfilRepository usuarioPerfilRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailLogin(username)
                .map(usuario -> new CustomUserPrincipal(
                        usuario,
                        usuarioPerfilRepository.findByUsuarioId(usuario.getIdUsuario().longValue())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + username));
    }
}
