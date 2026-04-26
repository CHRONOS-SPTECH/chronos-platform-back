package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TipoVinculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTipoVinculoRepository extends JpaRepository<TipoVinculo, Integer> {
}
