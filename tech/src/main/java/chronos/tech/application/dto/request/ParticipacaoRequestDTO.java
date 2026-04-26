package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ParticipacaoRequestDTO(
        @NotNull Long id_evento,
        @NotNull Long id_pessoa,
        @NotNull Long id_tipo_participacao,
        Boolean compareceu
) {
}
