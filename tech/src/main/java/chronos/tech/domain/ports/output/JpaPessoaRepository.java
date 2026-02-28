package chronos.tech.domain.ports.output;

import chronos.tech.domain.ports.input.JpaPessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPessoaRepository extends JpaRepository <JpaPessoaEntity, Long> {



}
