package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.AvaliacaoFormacaoTurma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AvaliacaoFormacaoTurmaRepository {
    Optional<AvaliacaoFormacaoTurma> findById(MatriculaTurmaId id);
    Page<AvaliacaoFormacaoTurma> findByTurmaIdTurma(Integer turmaId, Pageable pageable);
    AvaliacaoFormacaoTurma save(AvaliacaoFormacaoTurma avaliacao);
}
