package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.ParticipacaoEvento;

import java.util.List;
import java.util.Optional;

public interface ParticipacaoRepository {
    List<ParticipacaoEvento> findAll();
    Optional<ParticipacaoEvento> findById(Long id);
    ParticipacaoEvento save(ParticipacaoEvento participacaoEvento);
    void deleteById(Long id);
}
