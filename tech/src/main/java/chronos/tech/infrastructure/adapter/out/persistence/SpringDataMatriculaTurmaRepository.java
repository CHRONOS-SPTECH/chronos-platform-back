package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.compostas.MatriculaTurmaId;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.EventoDestinatario;
import chronos.tech.domain.model.enums.StatusTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataMatriculaTurmaRepository extends JpaRepository<MatriculaTurma, MatriculaTurmaId> {
    List<MatriculaTurma> findByTurmaIdTurma(Integer turmaId);
    List<MatriculaTurma> findByPessoaIdPessoa(Integer pessoaId);
    Page<MatriculaTurma> findByTurmaIdTurma(Integer turmaId, Pageable pageable);

    @Query(value = """
            SELECT DISTINCT p FROM pessoa p
            JOIN p.matriculaTurmas m
            WHERE p.tipoVinculo.nome_vinculo IN :vinculos
              AND p.dataSaida IS NULL
            """,
            countQuery = """
            SELECT COUNT(DISTINCT p.idPessoa) FROM pessoa p
            JOIN p.matriculaTurmas m
            WHERE p.tipoVinculo.nome_vinculo IN :vinculos
              AND p.dataSaida IS NULL
            """)
    Page<Pessoa> findAlunos(@Param("vinculos") List<String> vinculos, Pageable pageable);

    @Query("""
            SELECT new chronos.tech.domain.model.classes.EventoDestinatario(p.nome, p.email)
            FROM matricula_turma m
            JOIN m.pessoa p
            JOIN m.turma t
            WHERE t.idTurma IN :turmaIds
              AND t.statusTurma = :status
              AND t.dataInicio <= :hoje
              AND p.dataSaida IS NULL
              AND p.email IS NOT NULL
              AND TRIM(p.email) <> ''
            """)
    List<EventoDestinatario> findDestinatariosAtivosEmTurmasIniciadas(
            @Param("turmaIds") List<Integer> turmaIds,
            @Param("status") StatusTurma status,
            @Param("hoje") LocalDate hoje
    );
}
