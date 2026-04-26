package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UsuarioPerfilRequestDTO(
        @NotNull Long id_usuario,
        @NotNull Long id_perfil
) {
}
