package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface AulaRepository {
    List<Aula> findAll();
    Optional<Aula> findById(Integer id);
    Aula save(Aula aula);
    void deleteById(Integer id);

    List<Pessoa> findInstrutoresAtivos();
}
