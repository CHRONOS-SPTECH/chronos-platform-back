package chronos.tech.application.dto.cadastro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaAtividadeRequestDTO(
        @NotBlank @Size(max = 50) String nome,
        @NotBlank @Size(max = 50) String descricao
) {
}
