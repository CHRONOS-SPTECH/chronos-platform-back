package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataUsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from usuario u where u.emailLogin = :emailLogin")
    Optional<Usuario> findByEmailLogin(@Param("emailLogin") String emailLogin);
}
