package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.NivelFormacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataNivelFormacaoRepository extends JpaRepository<NivelFormacao, Long> {
}
