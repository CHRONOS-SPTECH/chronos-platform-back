package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.model.classes.UsuarioPerfil;

import java.util.List;
import java.util.Optional;

public interface UsuarioPerfilRepository {
    List<UsuarioPerfil> findAll();
    Optional<UsuarioPerfil> findById(Long id);
    List<UsuarioPerfil> findByUsuarioId(Long usuarioId);
    UsuarioPerfil save(UsuarioPerfil usuarioPerfil);
    void deleteById(Long id);
}
