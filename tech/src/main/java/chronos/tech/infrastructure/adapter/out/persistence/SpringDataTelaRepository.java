package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Tela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTelaRepository extends JpaRepository<Tela, Integer> {
}

