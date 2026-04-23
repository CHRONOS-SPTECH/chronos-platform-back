package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.PerfilAcesso;
import java.util.List;
import java.util.Optional;

public interface PerfilAcessoRepository {
    List<PerfilAcesso> findAll();

    Optional<PerfilAcesso> findById(Long id);

    PerfilAcesso save(PerfilAcesso perfilAcesso);

    void deleteById(Long id);
}
