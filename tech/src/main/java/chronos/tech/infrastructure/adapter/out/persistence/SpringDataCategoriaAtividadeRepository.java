package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.CategoriaAtividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCategoriaAtividadeRepository extends JpaRepository<CategoriaAtividade, Long> {
}
