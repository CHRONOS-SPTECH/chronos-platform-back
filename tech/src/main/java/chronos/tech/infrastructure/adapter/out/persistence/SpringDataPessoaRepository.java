package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByNome(String nome);
}
