package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Aula;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface AulaRepository {
    List<Aula> findAll();
    Optional<Aula> findById(Integer id);
    Aula save(Aula aula);
    void deleteById(Integer id);
    List<Aula> findByDataAula(Date data);
    List<Aula> findByDataAulaAndInstrutorIdPessoa(Date data, Integer instrutorId);

    // Busca se o professor tem aulas sobrepostas no mesmo período
    List<Aula> findByInstrutorIdPessoaAndDataAula(Integer idInstrutor, Date dataAula);

    // Busca se a turma já possui aulas sobrepostas no mesmo período
    List<Aula> findByTurmaIdTurmaAndDataAula(Integer idTurma, Date dataAula);
}
