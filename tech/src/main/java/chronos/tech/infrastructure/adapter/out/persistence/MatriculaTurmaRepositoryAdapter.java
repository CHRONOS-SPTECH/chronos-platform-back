package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatriculaTurmaRepositoryAdapter implements MatriculaTurmaRepository {
    private final SpringDataMatriculaTurmaRepository repository;

    @Override
    public List<MatriculaTurma> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MatriculaTurma> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public MatriculaTurma save(MatriculaTurma matriculaTurma) {
        return repository.save(matriculaTurma);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MatriculaTurma> findByTurmaIdTurma(Integer turmaId) {
        return repository.findByTurmaIdTurma(turmaId);
    }

    @Override
    public Page<MatriculaTurma> findByTurmaIdTurma(Integer turmaId, Pageable pageable) {
        return repository.findByTurmaIdTurma(turmaId, pageable);
    }

    @Override
    public Page<Pessoa> findAlunos(Pageable pageable) {
        return repository.findAlunos(pageable);
    }
}
