package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.TemaAula;

import java.util.List;
import java.util.Optional;

public interface TemaAulaRepository {
    List<TemaAula> findAll();
    Optional<TemaAula> findById(Long id);
    TemaAula save(TemaAula temaAula);
    void deleteById(Long id);
}
