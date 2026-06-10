package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChamadaAlunoRequestDTO(
        @NotNull Long id_pessoa,
        @NotNull Boolean compareceu
) {
}