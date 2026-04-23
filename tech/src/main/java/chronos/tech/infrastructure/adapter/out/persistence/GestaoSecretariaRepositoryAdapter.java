package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.GestaoSecretaria;
import chronos.tech.domain.port.GestaoSecretariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GestaoSecretariaRepositoryAdapter implements GestaoSecretariaRepository {
    private final SpringDataGestaoSecretariaRepository repository;

    @Override
    public List<GestaoSecretaria> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<GestaoSecretaria> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public GestaoSecretaria save(GestaoSecretaria gestaoSecretaria) {
        return repository.save(gestaoSecretaria);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
