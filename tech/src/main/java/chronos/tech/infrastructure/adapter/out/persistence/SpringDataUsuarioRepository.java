package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpringDataUsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from usuarios u where u.email_login = :emailLogin")
    Optional<Usuario> findByEmailLogin(String emailLogin);
}
