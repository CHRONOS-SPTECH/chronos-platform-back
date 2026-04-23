package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TipoParticipacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTipoParticipacaoRepository extends JpaRepository<TipoParticipacao, Long> {
}
