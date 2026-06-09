package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.port.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PessoaRepositoryAdapter implements PessoaRepository {

    private final SpringDataPessoaRepository repository;

    @Override
    public Pessoa save(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pessoa> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long countTotal() {
        return 0L;
    }

    @Override
    public Long countByTipoVinculo(String nome) {
        return 0L;
    }

    @Override
    public Long countMembrosAtivos() {
        return 0L;
    }

    @Override
    public List<Object[]> countByGenero() {
        return List.of();
    }

    @Override
    public List<LocalDate> findAllDatasNascimento() {
        return List.of();
    }

    @Override
    public List<Object[]> findDatasNascimentoEGenero() {
        return List.of();
    }

}
