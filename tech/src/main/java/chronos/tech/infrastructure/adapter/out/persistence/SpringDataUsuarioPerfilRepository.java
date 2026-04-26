package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataUsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Long> {
    @Query("select up from usuario_perfil up where up.usuario.idUsuario = :usuarioId")
    List<UsuarioPerfil> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
