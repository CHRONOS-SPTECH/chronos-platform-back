package chronos.tech.infrastructure.security;

import chronos.tech.domain.model.classes.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserPrincipal implements UserDetails {
    private final Usuario usuario;

    public CustomUserPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario usuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = usuario.getPerfil() != null && usuario.getPerfil().getNome_perfil() != null
                ? usuario.getPerfil().getNome_perfil().toUpperCase()
                : "USER";
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha_hash();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail_login();
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
        return Boolean.TRUE.equals(usuario.getStatus_ativo());
    }
}
