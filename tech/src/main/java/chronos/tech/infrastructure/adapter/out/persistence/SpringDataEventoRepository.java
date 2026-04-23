package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataEventoRepository extends JpaRepository<Evento, Long> {
}
