package chronos.tech.application.dto.request;

public record MovimentacaoAulaDTO(
        Integer idAula,
        String dataAula,
        String horaInicio
) {
}