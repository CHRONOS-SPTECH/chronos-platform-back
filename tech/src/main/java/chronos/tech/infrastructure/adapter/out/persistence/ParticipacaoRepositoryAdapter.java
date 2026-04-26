package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.ParticipacaoEvento;
import chronos.tech.domain.port.ParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParticipacaoRepositoryAdapter implements ParticipacaoRepository {
    private final SpringDataParticipacaoRepository repository;

    @Override
    public List<ParticipacaoEvento> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ParticipacaoEvento> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ParticipacaoEvento save(ParticipacaoEvento participacaoEvento) {
        return repository.save(participacaoEvento);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
