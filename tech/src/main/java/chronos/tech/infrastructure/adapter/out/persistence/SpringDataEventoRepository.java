package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataEventoRepository extends JpaRepository<Evento, Long> {
    @Query("""
            SELECT e FROM evento e
            WHERE e.dataEvento BETWEEN :start AND :end
              AND LOWER(e.statusEvento) IN ('agendado','publicado','oficializado','realizado','concluído','concluido')
              AND LOWER(e.statusEvento) NOT LIKE '%cancel%'
            """)
    List<Evento> findUpcomingEvents(@Param("start") LocalDate start,
                                    @Param("end") LocalDate end);

    @Query("""
            SELECT e FROM evento e
            WHERE e.dataEvento BETWEEN :start AND :end
              AND LOWER(e.statusEvento) NOT LIKE '%cancel%'
              AND (LOWER(e.statusEvento) LIKE '%realiz%' OR LOWER(e.statusEvento) LIKE '%conclu%')
            """)
    List<Evento> findCompletedEvents(@Param("start") LocalDate start,
                                     @Param("end") LocalDate end);
}
