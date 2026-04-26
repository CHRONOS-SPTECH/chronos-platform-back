package chronos.tech.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NivelFormacaoRequestDTO(
        @NotBlank @Size(max = 25) String nome_nivel,
        @NotNull @Min(1) Integer ordem_hierarquia
) {
}
