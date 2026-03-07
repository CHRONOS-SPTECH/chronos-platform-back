package chronos.tech.repository;

import chronos.tech.model.classes.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository <Pessoa, Long> {
}
