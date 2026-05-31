package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.TemaAula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataTemaAulaRepository extends JpaRepository<TemaAula, Long> {
    Optional<TemaAula> findByTituloTema(String tituloTema);
}
