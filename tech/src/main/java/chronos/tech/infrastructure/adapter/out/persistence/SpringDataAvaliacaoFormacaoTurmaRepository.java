package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.AvaliacaoFormacaoTurma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAvaliacaoFormacaoTurmaRepository
        extends JpaRepository<AvaliacaoFormacaoTurma, MatriculaTurmaId> {
    org.springframework.data.domain.Page<AvaliacaoFormacaoTurma> findByTurmaIdTurma(
            Integer turmaId, org.springframework.data.domain.Pageable pageable);
}
