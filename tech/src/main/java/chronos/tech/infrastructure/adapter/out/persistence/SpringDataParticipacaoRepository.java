package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.ParticipacaoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataParticipacaoRepository extends JpaRepository<ParticipacaoEvento, Long> {
}
