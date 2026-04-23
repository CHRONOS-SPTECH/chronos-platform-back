package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Participacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataParticipacaoRepository extends JpaRepository<Participacao, Long> {
}
