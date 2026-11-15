package chronos.tech.application.service;

import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO;
import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO.CategoriaAdesaoDTO;
import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO.EventoPerformanceDTO;
import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO.ImpactoEventosDTO;
import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO.PerformancePublicoDTO;
import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO.PublicoExternoDTO;
import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO.SecretariaDestaqueDTO;
import chronos.tech.application.port.in.DashboardEngajamentoUseCase;
import chronos.tech.domain.port.EngajamentoAtividadesRepository;
import chronos.tech.domain.port.EngajamentoAtividadesRepository.EventoParticipacaoResumo;
import chronos.tech.domain.port.EngajamentoAtividadesRepository.PessoaParticipanteResumo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Regras da Dashboard de Engajamento de Atividades.
 *
 * Considera apenas eventos com data igual ou anterior a hoje (eventos já realizados).
 * "Inscritos" são os registros em participacao_evento e "presentes" são os registros com compareceu = 1.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardEngajamentoService implements DashboardEngajamentoUseCase {

    /** Percentual mínimo de presença esperado para um evento. Abaixo disso o evento fica "abaixo da meta". */
    static final long META_PRESENCA = 70L;

    static final String VINCULO_PUBLICO_EXTERNO = "Público Externo";

    private final EngajamentoAtividadesRepository repository;

    @Override
    public DashboardEngajamentoResponseDTO getEngajamentoAtividades() {
        LocalDate hoje = LocalDate.now();

        List<EventoParticipacaoResumo> eventosRealizados = repository.findResumoEventosRealizadosAte(hoje);
        List<EventoParticipacaoResumo> eventosComInscricao = eventosRealizados.stream()
                .filter(evento -> evento.inscritos() > 0)
                .toList();

        return new DashboardEngajamentoResponseDTO(
                calcularImpacto(eventosComInscricao),
                calcularPublicoExterno(hoje),
                calcularSecretariaDestaque(eventosRealizados),
                calcularPerformanceEventos(eventosComInscricao),
                calcularAdesaoCategorias(eventosComInscricao),
                calcularPerformancePublico(eventosComInscricao),
                META_PRESENCA
        );
    }

    private ImpactoEventosDTO calcularImpacto(List<EventoParticipacaoResumo> eventos) {
        long presentes = eventos.stream().mapToLong(EventoParticipacaoResumo::presentes).sum();
        long inscritos = eventos.stream().mapToLong(EventoParticipacaoResumo::inscritos).sum();
        long adesaoMedia = Math.round(eventos.stream()
                .mapToDouble(evento -> percentualExato(evento.presentes(), evento.inscritos()))
                .average()
                .orElse(0));

        return new ImpactoEventosDTO(presentes, inscritos, (long) eventos.size(), adesaoMedia);
    }

    /**
     * Uma pessoa entra no alcance externo se hoje é Público Externo ou se se matriculou
     * em uma turma só depois do primeiro evento em que esteve presente (ou seja, chegou de fora).
     * Essas últimas são as que "tornaram-se alunos".
     */
    private PublicoExternoDTO calcularPublicoExterno(LocalDate hoje) {
        List<PessoaParticipanteResumo> participantes = repository.findParticipantesPresentesAte(hoje);
        Map<Integer, LocalDate> primeiraMatricula = repository.findPrimeiraMatriculaPorPessoa();

        long alcance = 0;
        long convertidos = 0;

        for (PessoaParticipanteResumo participante : participantes) {
            LocalDate matricula = primeiraMatricula.get(participante.idPessoa());
            boolean converteu = matricula != null
                    && participante.primeiraParticipacao() != null
                    && !matricula.isBefore(participante.primeiraParticipacao());
            boolean externo = VINCULO_PUBLICO_EXTERNO.equalsIgnoreCase(participante.vinculo());

            if (externo || converteu) alcance++;
            if (converteu) convertidos++;
        }

        return new PublicoExternoDTO(alcance, convertidos, percentual(convertidos, alcance));
    }

    /**
     * Secretaria com a maior taxa de presença (presentes / inscritos) em seus eventos.
     * Empate: vence a que realizou mais eventos.
     */
    private SecretariaDestaqueDTO calcularSecretariaDestaque(List<EventoParticipacaoResumo> eventos) {
        Map<String, long[]> porSecretaria = new LinkedHashMap<>(); // [eventos, inscritos, presentes]
        for (EventoParticipacaoResumo evento : eventos) {
            long[] totais = porSecretaria.computeIfAbsent(evento.secretaria(), chave -> new long[3]);
            totais[0]++;
            totais[1] += evento.inscritos();
            totais[2] += evento.presentes();
        }

        return porSecretaria.entrySet().stream()
                .filter(entrada -> entrada.getValue()[1] > 0)
                .map(entrada -> new SecretariaDestaqueDTO(
                        entrada.getKey(),
                        entrada.getValue()[0],
                        percentual(entrada.getValue()[2], entrada.getValue()[1])))
                .max(Comparator.comparingLong(SecretariaDestaqueDTO::taxa_sucesso)
                        .thenComparingLong(SecretariaDestaqueDTO::total_eventos))
                .orElse(null);
    }

    /** Eventos ordenados do maior para o menor percentual de presença. */
    private List<EventoPerformanceDTO> calcularPerformanceEventos(List<EventoParticipacaoResumo> eventos) {
        return eventos.stream()
                .map(evento -> {
                    long percentual = percentual(evento.presentes(), evento.inscritos());
                    return new EventoPerformanceDTO(
                            evento.idEvento(),
                            evento.titulo(),
                            evento.secretaria(),
                            evento.dataEvento(),
                            evento.inscritos(),
                            evento.presentes(),
                            percentual,
                            percentual < META_PRESENCA
                    );
                })
                .sorted(Comparator.comparingLong(EventoPerformanceDTO::percentual_presenca).reversed()
                        .thenComparing(Comparator.comparingLong(EventoPerformanceDTO::presentes).reversed())
                        .thenComparing(EventoPerformanceDTO::data_evento,
                                Comparator.nullsLast(Comparator.<LocalDate>reverseOrder())))
                .toList();
    }

    /** Percentual de presença por categoria de atividade, do maior para o menor. */
    private List<CategoriaAdesaoDTO> calcularAdesaoCategorias(List<EventoParticipacaoResumo> eventos) {
        Map<String, long[]> porCategoria = new LinkedHashMap<>(); // [inscritos, presentes]
        for (EventoParticipacaoResumo evento : eventos) {
            long[] totais = porCategoria.computeIfAbsent(evento.categoria(), chave -> new long[2]);
            totais[0] += evento.inscritos();
            totais[1] += evento.presentes();
        }

        return porCategoria.entrySet().stream()
                .map(entrada -> new CategoriaAdesaoDTO(
                        entrada.getKey(),
                        entrada.getValue()[0],
                        entrada.getValue()[1],
                        percentual(entrada.getValue()[1], entrada.getValue()[0])))
                .sorted(Comparator.comparingLong(CategoriaAdesaoDTO::percentual).reversed())
                .toList();
    }

    /**
     * Compara a média de presentes por evento de cada local com a média geral
     * e retorna o local com a maior média. Retorna null se nenhum evento tiver local informado.
     */
    private PerformancePublicoDTO calcularPerformancePublico(List<EventoParticipacaoResumo> eventos) {
        Map<String, String> nomeExibicao = new LinkedHashMap<>();
        Map<String, long[]> porLocal = new LinkedHashMap<>(); // [eventos, presentes]
        long totalEventos = 0;
        long totalPresentes = 0;

        for (EventoParticipacaoResumo evento : eventos) {
            if (evento.local() == null || evento.local().isBlank()) continue;

            String local = evento.local().trim();
            String chave = local.toLowerCase(Locale.ROOT);
            nomeExibicao.putIfAbsent(chave, local);

            long[] totais = porLocal.computeIfAbsent(chave, c -> new long[2]);
            totais[0]++;
            totais[1] += evento.presentes();
            totalEventos++;
            totalPresentes += evento.presentes();
        }

        if (totalEventos == 0) return null;

        double mediaGeral = (double) totalPresentes / totalEventos;

        Map.Entry<String, long[]> melhor = porLocal.entrySet().stream()
                .max(Comparator.<Map.Entry<String, long[]>>comparingDouble(
                                entrada -> (double) entrada.getValue()[1] / entrada.getValue()[0])
                        .thenComparingLong(entrada -> entrada.getValue()[0]))
                .orElseThrow();

        double mediaLocal = (double) melhor.getValue()[1] / melhor.getValue()[0];
        long acimaDaMedia = mediaGeral > 0 ? Math.round((mediaLocal / mediaGeral - 1) * 100) : 0L;

        return new PerformancePublicoDTO(
                nomeExibicao.get(melhor.getKey()),
                melhor.getValue()[0],
                arredondar(mediaLocal),
                arredondar(mediaGeral),
                acimaDaMedia
        );
    }

    private static long percentual(long parte, long total) {
        return Math.round(percentualExato(parte, total));
    }

    private static double percentualExato(long parte, long total) {
        return total > 0 ? parte * 100.0 / total : 0.0;
    }

    private static double arredondar(double valor) {
        return Math.round(valor * 10) / 10.0;
    }
}
