package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.PerfilAcesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPerfilAcessoRepository extends JpaRepository<PerfilAcesso, Long> {
}
