package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.EventoDestinatario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatriculaTurmaRepository {
    List<MatriculaTurma> findAll();
    Optional<MatriculaTurma> findById(MatriculaTurmaId id);
    MatriculaTurma save(MatriculaTurma matriculaTurma);
    void deleteById(MatriculaTurmaId id);
    List<MatriculaTurma> findByTurmaIdTurma(Integer turmaId);
    List<MatriculaTurma> findByPessoaIdPessoa(Integer pessoaId);
    Page<MatriculaTurma> findByTurmaIdTurma(Integer turmaId, Pageable pageable);
    Page<Pessoa> findAlunos(Pageable pageable);
    List<EventoDestinatario> findDestinatariosAtivosEmTurmasIniciadas(List<Integer> turmaIds, LocalDate hoje);
}
