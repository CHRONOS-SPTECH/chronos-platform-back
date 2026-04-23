package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.MatriculaTurma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMatriculaTurmaRepository extends JpaRepository<MatriculaTurma, Long> {
}
