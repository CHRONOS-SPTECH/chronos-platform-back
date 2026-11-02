package chronos.tech.application.dto.chamada;

import jakarta.validation.constraints.NotNull;

public record ChamadaAulaRequestDTO(
        @NotNull Long id_aula,
        @NotNull Long id_pessoa,
        Boolean compareceu
) {
}
