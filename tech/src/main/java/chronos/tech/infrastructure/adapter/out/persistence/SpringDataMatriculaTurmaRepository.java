package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataMatriculaTurmaRepository extends JpaRepository<MatriculaTurma, Long> {
    List<MatriculaTurma> findByTurmaIdTurma(Integer turmaId);
    Page<MatriculaTurma> findByTurmaIdTurma(Integer turmaId, Pageable pageable);

    @Query(value = "SELECT DISTINCT p FROM pessoa p JOIN p.matriculaTurmas m",
            countQuery = "SELECT COUNT(DISTINCT p.idPessoa) FROM pessoa p JOIN p.matriculaTurmas m")
    Page<Pessoa> findAlunos(Pageable pageable);
}
