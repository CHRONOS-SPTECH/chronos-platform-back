package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.EventoDestinatario;
import chronos.tech.domain.model.enums.StatusTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataMatriculaTurmaRepository extends JpaRepository<MatriculaTurma, Long> {
    List<MatriculaTurma> findByTurmaIdTurma(Integer turmaId);

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
