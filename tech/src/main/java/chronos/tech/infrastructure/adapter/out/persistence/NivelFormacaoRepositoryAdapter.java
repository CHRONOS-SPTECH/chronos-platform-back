package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.NivelFormacao;
import chronos.tech.domain.port.NivelFormacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NivelFormacaoRepositoryAdapter implements NivelFormacaoRepository {

    private final SpringDataNivelFormacaoRepository repository;

    @Override
    public List<NivelFormacao> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<NivelFormacao> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public NivelFormacao save(NivelFormacao nivelFormacao) {
        return repository.save(nivelFormacao);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
