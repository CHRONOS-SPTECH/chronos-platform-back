package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Tela;

import java.util.List;
import java.util.Optional;

public interface TelaRepository {
    List<Tela> findAll();
    Optional<Tela> findById(Integer id);
}

