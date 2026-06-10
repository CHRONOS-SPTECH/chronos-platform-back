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
        return repository.countTotal();
    }

    @Override
    public Long countByTipoVinculo(String nome) {
        return repository.countByTipoVinculo(nome);
    }

    @Override
    public Long countMembrosAtivos() {
        return repository.countMembrosAtivos();
    }

    @Override
    public List<Object[]> countByGenero() {
        return repository.countByGenero();
    }

    @Override
    public List<LocalDate> findAllDatasNascimento() {
        return repository.findAllDatasNascimento();
    }

    @Override
    public List<Object[]> findDatasNascimentoEGenero() {
        return repository.findDatasNascimentoEGenero();
    }

    @Override
    public Optional<Pessoa> findByNome(String nome) {
        return repository.findByNome(nome);
    }
}