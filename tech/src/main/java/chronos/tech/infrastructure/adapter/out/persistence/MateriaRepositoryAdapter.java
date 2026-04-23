package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Materia;
import chronos.tech.domain.port.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MateriaRepositoryAdapter implements MateriaRepository {
    private final SpringDataMateriaRepository repository;

    @Override
    public List<Materia> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Materia> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Materia save(Materia materia) {
        return repository.save(materia);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
