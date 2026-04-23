package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMateriaRepository extends JpaRepository<Materia, Long> {
}
