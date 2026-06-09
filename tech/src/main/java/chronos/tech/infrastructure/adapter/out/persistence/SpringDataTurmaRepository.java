package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataTurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT COUNT(t) FROM turma t WHERE t.statusTurma = :status")
    Long countByStatus(@Param("status") StatusTurma status);

}
