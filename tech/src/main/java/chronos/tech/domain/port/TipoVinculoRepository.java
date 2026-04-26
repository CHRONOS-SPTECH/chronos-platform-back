package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.TipoVinculo;

import java.util.List;
import java.util.Optional;

public interface TipoVinculoRepository {
    List<TipoVinculo> findAll();
    Optional<TipoVinculo> findById(Integer id);
    TipoVinculo save(TipoVinculo tipoVinculo);
    void deleteById(Integer id);
}
