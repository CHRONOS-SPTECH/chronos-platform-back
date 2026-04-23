package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.GestaoSecretaria;

import java.util.List;
import java.util.Optional;

public interface GestaoSecretariaRepository {
    List<GestaoSecretaria> findAll();
    Optional<GestaoSecretaria> findById(Long id);
    GestaoSecretaria save(GestaoSecretaria gestaoSecretaria);
    void deleteById(Long id);
}
