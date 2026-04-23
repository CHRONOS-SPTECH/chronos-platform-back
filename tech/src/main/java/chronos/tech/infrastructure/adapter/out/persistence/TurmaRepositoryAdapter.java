package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TurmaRepositoryAdapter implements TurmaRepository {

    private final SpringDataTurmaRepository repository;

    @Override
    public List<Turma> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Turma> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Turma save(Turma turma) {
        return repository.save(turma);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
