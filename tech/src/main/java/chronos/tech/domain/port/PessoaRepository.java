package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.EventoDestinatario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findById(Long id);

    Page<Pessoa> findByTipoVinculo(String nomeVinculo, Pageable pageable);

    Long countTotal();

    Long countByTipoVinculo(String nome);

    Long countMembrosAtivos();

    List<Object[]> countByGenero();

    List<LocalDate> findAllDatasNascimento();

    List<Object[]> findDatasNascimentoEGenero();
    Optional<Pessoa> findByNome(String nome);
    List<EventoDestinatario> findPublicoExternoAtivoSemMatricula();
}
