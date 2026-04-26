package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MateriaRequestDTO(
        @NotBlank @Size(max = 100) String nome,
        @NotNull Long id_nivel
) {
}
