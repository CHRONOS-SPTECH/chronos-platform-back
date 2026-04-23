package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.TipoParticipacao;
import java.util.List;
import java.util.Optional;

public interface TipoParticipacaoRepository {
    List<TipoParticipacao> findAll();

    Optional<TipoParticipacao> findById(Long id);

    TipoParticipacao save(TipoParticipacao tipoParticipacao);

    void deleteById(Long id);
}
