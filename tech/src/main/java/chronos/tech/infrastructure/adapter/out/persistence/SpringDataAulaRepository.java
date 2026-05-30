package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface SpringDataAulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByDataAula(Date data);
    List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId);
    List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula);
    List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula);
}