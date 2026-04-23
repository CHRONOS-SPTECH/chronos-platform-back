package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaRepository {
    List<Materia> findAll();
    Optional<Materia> findById(Long id);
    Materia save(Materia materia);
    void deleteById(Long id);
}
