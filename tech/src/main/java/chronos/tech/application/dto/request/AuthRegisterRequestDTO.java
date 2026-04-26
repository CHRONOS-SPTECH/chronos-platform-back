package chronos.tech.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthRegisterRequestDTO(
        @Valid @NotNull PessoaRequestDTO pessoa,
        @NotEmpty
        List<Long> perfis_id,
        @NotBlank @Email @Size(max = 150) String email_login,
        @NotBlank @Size(min = 6, max = 255) String senha
) {
}
