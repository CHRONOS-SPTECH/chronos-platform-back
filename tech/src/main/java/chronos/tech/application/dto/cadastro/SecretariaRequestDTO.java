package chronos.tech.application.dto.cadastro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SecretariaRequestDTO(
        @NotBlank @Size(max = 100) String nome_secretaria,
        @Size(max = 255) String descricao_secretaria
) {
}
