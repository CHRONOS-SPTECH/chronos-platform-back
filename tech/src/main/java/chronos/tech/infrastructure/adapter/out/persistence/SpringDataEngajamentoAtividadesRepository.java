package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.ParticipacaoEvento;
import chronos.tech.domain.model.classes.compostas.ParticipacaoEventoId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Consultas somente leitura da Dashboard de Engajamento de Atividades.
 * Estende {@link Repository} (e não JpaRepository) para não expor métodos de escrita.
 */
public interface SpringDataEngajamentoAtividadesRepository extends Repository<ParticipacaoEvento, ParticipacaoEventoId> {

    // [0] id_evento, [1] titulo, [2] data_evento, [3] local_evento,
    // [4] nome_secretaria, [5] nome_categoria, [6] inscritos, [7] presentes
    @Query("""
            SELECT e.idEvento, e.titulo, e.dataEvento, e.localEvento,
                   s.nomeSecretaria, c.nome,
                   COUNT(p.id.idPessoa),
                   COALESCE(SUM(CASE WHEN p.compareceu = true THEN 1 ELSE 0 END), 0)
            FROM evento e
            JOIN e.idSecretaria s
            JOIN e.idCategoria c
            LEFT JOIN e.participacaoEventoLong p
            WHERE e.dataEvento <= :data
            GROUP BY e.idEvento, e.titulo, e.dataEvento, e.localEvento, s.nomeSecretaria, c.nome
            """)
    List<Object[]> findResumoEventosRealizadosAte(@Param("data") LocalDate data);

    // [0] id_pessoa, [1] nome_vinculo, [2] data do primeiro evento com presença
    @Query("""
            SELECT p.id.idPessoa, tv.nome_vinculo, MIN(e.dataEvento)
            FROM participacao_evento p
            JOIN p.evento e
            JOIN p.pessoa pe
            JOIN pe.tipoVinculo tv
            WHERE p.compareceu = true
              AND e.dataEvento <= :data
            GROUP BY p.id.idPessoa, tv.nome_vinculo
            """)
    List<Object[]> findParticipantesPresentesAte(@Param("data") LocalDate data);

    // [0] id_pessoa, [1] data da primeira matrícula
    @Query("""
            SELECT m.id.idPessoa, MIN(m.dataMatricula)
            FROM matricula_turma m
            GROUP BY m.id.idPessoa
            """)
    List<Object[]> findPrimeiraMatriculaPorPessoa();
}
