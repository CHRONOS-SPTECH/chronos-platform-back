package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.MatriculaTurma;

import java.util.List;
import java.util.Optional;

public interface MatriculaTurmaRepository {
    List<MatriculaTurma> findAll();
    Optional<MatriculaTurma> findById(Long id);
    MatriculaTurma save(MatriculaTurma matriculaTurma);
    void deleteById(Long id);
}
