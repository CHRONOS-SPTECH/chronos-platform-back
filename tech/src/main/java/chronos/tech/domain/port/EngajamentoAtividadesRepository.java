package chronos.tech.domain.port;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Porta de saída com as consultas usadas pela Dashboard de Engajamento de Atividades.
 */
public interface EngajamentoAtividadesRepository {

    /**
     * Eventos realizados até a data informada, com o total de inscritos e de presentes.
     * Eventos sem nenhuma inscrição também são retornados (inscritos = 0).
     */
    List<EventoParticipacaoResumo> findResumoEventosRealizadosAte(LocalDate data);

    /**
     * Pessoas que compareceram a pelo menos um evento realizado até a data informada,
     * com o vínculo atual e a data do primeiro evento em que estiveram presentes.
     */
    List<PessoaParticipanteResumo> findParticipantesPresentesAte(LocalDate data);

    /**
     * Data da primeira matrícula em turma de cada pessoa (chave = id da pessoa).
     */
    Map<Integer, LocalDate> findPrimeiraMatriculaPorPessoa();

    record EventoParticipacaoResumo(
            Integer idEvento,
            String titulo,
            LocalDate dataEvento,
            String local,
            String secretaria,
            String categoria,
            long inscritos,
            long presentes
    ) {}

    record PessoaParticipanteResumo(
            Integer idPessoa,
            String vinculo,
            LocalDate primeiraParticipacao
    ) {}
}
