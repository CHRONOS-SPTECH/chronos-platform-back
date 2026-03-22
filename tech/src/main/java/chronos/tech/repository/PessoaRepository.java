package chronos.tech.repository;

import chronos.tech.model.classes.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository <Pessoa, Long> {



}
