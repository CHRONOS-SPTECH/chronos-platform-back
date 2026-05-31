package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataTurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByNomeTurma(String nomeTurma);
}
