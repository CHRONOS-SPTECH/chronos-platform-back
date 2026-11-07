package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Component
@RequiredArgsConstructor
public class TurmaRepositoryAdapter implements TurmaRepository {

    private final SpringDataTurmaRepository repository;

    @Override
    public List<Turma> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Turma> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Turma> findById(Long id) {
        try {
            return repository.findById(Math.toIntExact(id));
        } catch (ArithmeticException e) {
            return Optional.empty();
        }
    }

    @Override
    public Turma save(Turma turma) {
        return repository.save(turma);
    }

    @Override
    public Long countByStatus(StatusTurma status) {
        return repository.countByStatus(status);
    }

    @Override
    public Optional<Turma> findByNomeTurma(String nomeTurma) {
        return repository.findByNomeTurma(nomeTurma);
    }
}
