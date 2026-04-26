package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.EnderecoPessoa;

import java.util.List;
import java.util.Optional;

public interface EnderecoPessoaRepository {
    List<EnderecoPessoa> findAll();
    Optional<EnderecoPessoa> findById(Integer id);
    EnderecoPessoa save(EnderecoPessoa enderecoPessoa);
    void deleteById(Integer id);
}
