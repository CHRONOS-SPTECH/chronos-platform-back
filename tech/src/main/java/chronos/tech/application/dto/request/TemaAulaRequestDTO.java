package chronos.tech.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TemaAulaRequestDTO(
        @NotBlank @Size(max = 150) String titulo_tema,
        @NotNull @Min(1) Integer ordem_prevista,
        @NotNull Long id_materia
) {
}
