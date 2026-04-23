package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TemaAula;
import chronos.tech.domain.port.TemaAulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TemaAulaRepositoryAdapter implements TemaAulaRepository {
    private final SpringDataTemaAulaRepository repository;

    @Override
    public List<TemaAula> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TemaAula> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TemaAula save(TemaAula temaAula) {
        return repository.save(temaAula);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
