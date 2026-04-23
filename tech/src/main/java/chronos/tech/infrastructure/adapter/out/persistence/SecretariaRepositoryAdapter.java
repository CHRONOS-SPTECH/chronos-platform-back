package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Secretaria;
import chronos.tech.domain.port.SecretariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecretariaRepositoryAdapter implements SecretariaRepository {

    private final SpringDataSecretariaRepository repository;

    @Override
    public List<Secretaria> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Secretaria> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Secretaria save(Secretaria secretaria) {
        return repository.save(secretaria);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
