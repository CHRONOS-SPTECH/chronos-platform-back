package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.CategoriaAtividade;
import java.util.List;
import java.util.Optional;

public interface CategoriaAtividadeRepository {
    List<CategoriaAtividade> findAll();

    Optional<CategoriaAtividade> findById(Long id);

    CategoriaAtividade save(CategoriaAtividade categoriaAtividade);

    void deleteById(Long id);
}
