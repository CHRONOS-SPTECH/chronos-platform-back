package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.EnderecoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataEnderecoPessoaRepository extends JpaRepository<EnderecoPessoa, Integer> {
}
