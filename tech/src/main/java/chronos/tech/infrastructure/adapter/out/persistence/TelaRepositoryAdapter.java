package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Tela;
import chronos.tech.domain.port.TelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TelaRepositoryAdapter implements TelaRepository {
    private final SpringDataTelaRepository repository;

    @Override
    public List<Tela> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Tela> findById(Integer id) {
        return repository.findById(id);
    }
}

