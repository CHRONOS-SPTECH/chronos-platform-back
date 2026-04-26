package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoVinculoRequestDTO(
        @NotBlank @Size(max = 50) String nome_vinculo,
        @Size(max = 255) String descricao
) {
}
