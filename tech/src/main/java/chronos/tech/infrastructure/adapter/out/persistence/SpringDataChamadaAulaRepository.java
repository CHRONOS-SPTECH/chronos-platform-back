package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.ChamadaAula;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataChamadaAulaRepository extends JpaRepository<ChamadaAula, Long> {
    List<ChamadaAula> findByPessoaIdPessoaAndAulaTurmaIdTurma(Integer pessoaId, Integer turmaId);

    List<ChamadaAula> findByPessoaIdPessoaInAndAulaTurmaIdTurma(List<Integer> pessoaIds, Integer turmaId);

    @EntityGraph(attributePaths = {"aula", "pessoa", "pessoa.tipoVinculo"})
    List<ChamadaAula> findByAulaTurmaIdTurma(Integer turmaId);

    Boolean existsByAula(Aula aula);

    List<ChamadaAula> findByAulaIdAula(Long id_aula);

    @EntityGraph(attributePaths = {"aula", "pessoa", "pessoa.tipoVinculo"})
    List<ChamadaAula> findByAulaIdAulaIn(List<Long> aulaIds);
}
