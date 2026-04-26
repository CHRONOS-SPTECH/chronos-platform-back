package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.TipoHoraGerada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoParticipacaoRequestDTO(
        @NotBlank @Size(max = 50) String descricao,
        @NotNull TipoHoraGerada tipo_hora_gerada
) {
}
