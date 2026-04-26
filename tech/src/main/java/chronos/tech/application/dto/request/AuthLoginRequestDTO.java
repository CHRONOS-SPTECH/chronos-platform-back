package chronos.tech.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequestDTO(
        @NotBlank @Email @Size(max = 150) String email_login,
        @NotBlank @Size(min = 6, max = 255) String senha
) {
}
