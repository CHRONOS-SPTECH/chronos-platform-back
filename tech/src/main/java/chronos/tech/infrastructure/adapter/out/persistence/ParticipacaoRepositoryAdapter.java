package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Participacao;
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
    public List<Participacao> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Participacao> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Participacao save(Participacao participacao) {
        return repository.save(participacao);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
