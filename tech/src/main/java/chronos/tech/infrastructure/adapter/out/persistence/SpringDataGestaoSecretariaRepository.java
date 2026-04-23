package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.GestaoSecretaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataGestaoSecretariaRepository extends JpaRepository<GestaoSecretaria, Long> {
}
