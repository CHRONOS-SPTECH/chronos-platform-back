package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface SpringDataAulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId);
    List<Aula> findByTurmaIdTurma(Integer idTurma);
    List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula);
    List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula);
    List<Aula> findByInstrutorIdPessoaAndDataAulaAndIdAulaNot(Integer idInstrutor, Date dataAula, Integer idAula);
    List<Aula> findByTurmaIdTurmaAndDataAulaAndIdAulaNot(Integer idTurma, Date dataAula, Integer idAula);
}
