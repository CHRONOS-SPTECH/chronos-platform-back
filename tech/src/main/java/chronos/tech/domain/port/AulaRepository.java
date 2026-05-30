package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Aula;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AulaRepository {
    List<Aula> findAll();
    Optional<Aula> findById(Integer id);
    Aula save(Aula aula);
    void deleteById(Integer id);
    List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId);
    List<Aula> findByTurmaIdTurma(Integer idTurma);
}
