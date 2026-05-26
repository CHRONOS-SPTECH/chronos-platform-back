package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Turma;
import java.util.List;
import java.util.Optional;

public interface TurmaRepository {
    List<Turma> findAll();

    Optional<Turma> findById(Long id);

    Turma save(Turma turma);

    void deleteById(Long id);

    Optional<Turma> findByNomeTurma(String nomeTurma);
}
