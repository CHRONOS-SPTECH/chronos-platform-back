package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusTurma;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository {
    List<Turma> findAll();

    Optional<Turma> findById(Long id);

    Turma save(Turma turma);

    void deleteById(Long id);

    Long countByStatus(StatusTurma status);
}
