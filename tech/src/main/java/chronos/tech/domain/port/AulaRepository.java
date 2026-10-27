package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AulaRepository {
    List<Aula> findAll();
    Page<Aula> findAll(Pageable pageable);
    Optional<Aula> findById(Integer id);
    Aula save(Aula aula);
    void deleteById(Integer id);

    List<Pessoa> findInstrutoresAtivos();
    List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId);
    Page<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId, Pageable pageable);
    List<Aula> findByTurmaIdTurma(Integer idTurma);
    Page<Aula> findByTurmaIdTurma(Integer idTurma, Pageable pageable);

    // Busca se o professor tem aulas sobrepostas no mesmo período
    List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula);

    // Busca se a turma já possui aulas sobrepostas no mesmo período
    List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula);

    List<Aula> findByInstrutorIdPessoaAndDataAulaAndIdAulaNot(Integer idInstrutor, Date dataAula, Integer idAula);
    List<Aula> findByTurmaIdTurmaAndDataAulaAndIdAulaNot(Integer idTurma, Date dataAula, Integer idAula);
}
