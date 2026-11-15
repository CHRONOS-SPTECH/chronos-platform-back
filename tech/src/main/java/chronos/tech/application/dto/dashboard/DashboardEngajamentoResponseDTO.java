package chronos.tech.application.dto.dashboard;

import java.time.LocalDate;
import java.util.List;

public record DashboardEngajamentoResponseDTO(
        ImpactoEventosDTO impacto_eventos,
        PublicoExternoDTO publico_externo,
        SecretariaDestaqueDTO secretaria_destaque,
        List<EventoPerformanceDTO> performance_eventos,
        List<CategoriaAdesaoDTO> adesao_categorias,
        PerformancePublicoDTO performance_publico,
        Long meta_presenca
) {
    /**
     * total_participantes: soma das presenças em eventos realizados.
     * adesao_media: média do percentual de presença (presentes / inscritos) dos eventos.
     */
    public record ImpactoEventosDTO(
            Long total_participantes,
            Long total_inscritos,
            Long total_eventos,
            Long adesao_media
    ) {}

    /**
     * alcance: pessoas de fora da escola que estiveram presentes em algum evento.
     * convertidos: quantas delas se matricularam em uma turma depois do primeiro evento.
     */
    public record PublicoExternoDTO(
            Long alcance,
            Long convertidos,
            Long taxa_matricula
    ) {}

    public record SecretariaDestaqueDTO(
            String nome,
            Long total_eventos,
            Long taxa_sucesso
    ) {}

    public record EventoPerformanceDTO(
            Integer id_evento,
            String titulo,
            String secretaria,
            LocalDate data_evento,
            Long inscritos,
            Long presentes,
            Long percentual_presenca,
            Boolean abaixo_da_meta
    ) {}

    public record CategoriaAdesaoDTO(
            String categoria,
            Long inscritos,
            Long presentes,
            Long percentual
    ) {}

    /**
     * Local com a maior média de presentes por evento, comparado à média geral.
     */
    public record PerformancePublicoDTO(
            String local,
            Long total_eventos,
            Double media_presentes_local,
            Double media_presentes_geral,
            Long percentual_acima_media
    ) {}
}
