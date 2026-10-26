package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.EventoDestinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

public interface SpringDataPessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT COUNT(p) FROM pessoa p")
    Long countTotal();

    @Query("SELECT COUNT(p) FROM pessoa p WHERE p.tipoVinculo.nome_vinculo = :nome")
    Long countByTipoVinculo(@Param("nome") String nome);

    @Query("SELECT COUNT(p) FROM pessoa p WHERE p.dataMembro IS NOT NULL AND p.dataSaida IS NULL")
    Long countMembrosAtivos();

    @Query("SELECT p.genero, COUNT(p) FROM pessoa p GROUP BY p.genero")
    List<Object[]> countByGenero();

    @Query("SELECT p.dataNascimento FROM pessoa p WHERE p.dataNascimento IS NOT NULL")
    List<LocalDate> findAllDatasNascimento();

    @Query("SELECT p.dataNascimento, p.genero FROM pessoa p WHERE p.dataNascimento IS NOT NULL")
    List<Object[]> findDatasNascimentoEGenero();

    Optional<Pessoa> findByNome(String nome);

    @Query("""
            SELECT new chronos.tech.domain.model.classes.EventoDestinatario(p.nome, p.email)
            FROM pessoa p
            WHERE p.tipoVinculo.nome_vinculo = :tipoVinculo
              AND p.dataSaida IS NULL
              AND p.email IS NOT NULL
              AND TRIM(p.email) <> ''
              AND NOT EXISTS (
                  SELECT m.id FROM matricula_turma m WHERE m.pessoa = p
              )
            """)
    List<EventoDestinatario> findPublicoExternoAtivoSemMatricula(
            @Param("tipoVinculo") String tipoVinculo
    );
}
