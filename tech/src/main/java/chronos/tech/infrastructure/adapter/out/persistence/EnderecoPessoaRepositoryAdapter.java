package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.EnderecoPessoa;
import chronos.tech.domain.port.EnderecoPessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EnderecoPessoaRepositoryAdapter implements EnderecoPessoaRepository {
    private final SpringDataEnderecoPessoaRepository repository;
    public List<EnderecoPessoa> findAll() { return repository.findAll(); }
    public Optional<EnderecoPessoa> findById(Integer id) { return repository.findById(id); }
    public EnderecoPessoa save(EnderecoPessoa enderecoPessoa) { return repository.save(enderecoPessoa); }
    public void deleteById(Integer id) { repository.deleteById(id); }
}
