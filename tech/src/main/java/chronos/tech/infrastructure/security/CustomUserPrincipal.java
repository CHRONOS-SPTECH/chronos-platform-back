package chronos.tech.infrastructure.security;

import chronos.tech.domain.model.classes.Usuario;
import chronos.tech.domain.model.classes.UsuarioPerfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserPrincipal implements UserDetails {
    private final Usuario usuario;
    private final List<UsuarioPerfil> perfis;

    public CustomUserPrincipal(Usuario usuario, List<UsuarioPerfil> perfis) {
        this.usuario = usuario;
        this.perfis = perfis;
    }

    public Usuario usuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (perfis == null || perfis.isEmpty()) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return perfis.stream()
                .map(v -> v.getPerfil() != null ? v.getPerfil().getNomePerfil() : null)
                .filter(nome -> nome != null && !nome.isBlank())
                .map(nome -> new SimpleGrantedAuthority("ROLE_" + nome.toUpperCase()))
                .toList();
    }

    @Override
    public String getPassword() {
        return usuario.getSenhaHash();
    }

    @Override
    public String getUsername() {
        return usuario.getEmailLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(usuario.getStatusAtivo());
    }
}
