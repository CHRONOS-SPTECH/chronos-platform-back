package chronos.tech.application.dto.request;

public record ParticipacaoRequestDTO(Long id_evento, Long id_pessoa, Long id_tipo_participacao, Boolean compareceu) {
}
