package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Participacao;

import java.util.List;
import java.util.Optional;

public interface ParticipacaoRepository {
    List<Participacao> findAll();
    Optional<Participacao> findById(Long id);
    Participacao save(Participacao participacao);
    void deleteById(Long id);
}
