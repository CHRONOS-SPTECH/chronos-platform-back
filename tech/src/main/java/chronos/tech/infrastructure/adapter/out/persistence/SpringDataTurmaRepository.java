package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTurmaRepository extends JpaRepository<Turma, Long> {
}
