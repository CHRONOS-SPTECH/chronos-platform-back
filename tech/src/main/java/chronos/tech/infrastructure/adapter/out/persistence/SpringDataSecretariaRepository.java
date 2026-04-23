package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSecretariaRepository extends JpaRepository<Secretaria, Long> {
}
