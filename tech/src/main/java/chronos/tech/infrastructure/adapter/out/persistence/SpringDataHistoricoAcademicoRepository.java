package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.HistoricoAcademico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataHistoricoAcademicoRepository extends JpaRepository<HistoricoAcademico, Long> {
}
