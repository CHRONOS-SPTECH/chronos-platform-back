package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.NivelFormacao;
import java.util.List;
import java.util.Optional;

public interface NivelFormacaoRepository {
    List<NivelFormacao> findAll();

    Optional<NivelFormacao> findById(Long id);

    NivelFormacao save(NivelFormacao nivelFormacao);

    void deleteById(Long id);
}
