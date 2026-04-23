package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPessoaRepository extends JpaRepository<Pessoa, Long> {
}
