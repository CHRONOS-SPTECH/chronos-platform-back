package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PerfilAcessoRequestDTO(
        @NotBlank @Size(max = 50) String nome_perfil,
        @Size(max = 255) String descricao
) {
}
