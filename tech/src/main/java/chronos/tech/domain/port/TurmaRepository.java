package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusTurma;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TurmaRepository {
    List<Turma> findAll();
    Page<Turma> findAll(Pageable pageable);

    Optional<Turma> findById(Long id);

    Turma save(Turma turma);

    Long countByStatus(StatusTurma status);
    Optional<Turma> findByNomeTurma(String nomeTurma);
}
