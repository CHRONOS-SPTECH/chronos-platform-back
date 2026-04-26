package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.ChamadaAula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataChamadaAulaRepository extends JpaRepository<ChamadaAula, Long> {
}
