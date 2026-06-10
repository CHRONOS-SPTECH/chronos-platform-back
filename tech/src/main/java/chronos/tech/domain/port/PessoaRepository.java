package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findById(Long id);

    List<Pessoa> findAll();

    void deleteById(Long id);

    Long countTotal();

    Long countByTipoVinculo(String nome);

    Long countMembrosAtivos();

    List<Object[]> countByGenero();

    List<LocalDate> findAllDatasNascimento();

    List<Object[]> findDatasNascimentoEGenero();
    Optional<Pessoa> findByNome(String nome);
}
