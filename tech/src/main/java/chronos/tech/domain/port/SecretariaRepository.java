package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Secretaria;
import java.util.List;
import java.util.Optional;

public interface SecretariaRepository {
    List<Secretaria> findAll();

    Optional<Secretaria> findById(Long id);

    Secretaria save(Secretaria secretaria);

    void deleteById(Long id);
}
