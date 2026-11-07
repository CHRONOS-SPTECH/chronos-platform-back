package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.AvaliacaoFormacaoTurma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import chronos.tech.domain.port.AvaliacaoFormacaoTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AvaliacaoFormacaoTurmaRepositoryAdapter implements AvaliacaoFormacaoTurmaRepository {
    private final SpringDataAvaliacaoFormacaoTurmaRepository repository;

    @Override
    public Optional<AvaliacaoFormacaoTurma> findById(MatriculaTurmaId id) {
        return repository.findById(id);
    }

    @Override
    public Page<AvaliacaoFormacaoTurma> findByTurmaIdTurma(Integer turmaId, Pageable pageable) {
        return repository.findByTurmaIdTurma(turmaId, pageable);
    }

    @Override
    public AvaliacaoFormacaoTurma save(AvaliacaoFormacaoTurma avaliacao) {
        return repository.save(avaliacao);
    }
}
