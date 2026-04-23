package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TipoParticipacao;
import chronos.tech.domain.port.TipoParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoParticipacaoRepositoryAdapter implements TipoParticipacaoRepository {

    private final SpringDataTipoParticipacaoRepository repository;

    @Override
    public List<TipoParticipacao> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TipoParticipacao> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TipoParticipacao save(TipoParticipacao tipoParticipacao) {
        return repository.save(tipoParticipacao);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
