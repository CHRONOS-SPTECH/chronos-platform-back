package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.CategoriaAtividade;
import chronos.tech.domain.port.CategoriaAtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoriaAtividadeRepositoryAdapter implements CategoriaAtividadeRepository {

    private final SpringDataCategoriaAtividadeRepository repository;

    @Override
    public List<CategoriaAtividade> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CategoriaAtividade> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public CategoriaAtividade save(CategoriaAtividade categoriaAtividade) {
        return repository.save(categoriaAtividade);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
