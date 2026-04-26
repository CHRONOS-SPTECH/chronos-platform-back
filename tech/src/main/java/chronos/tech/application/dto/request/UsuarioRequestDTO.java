package chronos.tech.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @Valid @NotNull PessoaRequestDTO pessoa,
        @NotBlank @Email @Size(max = 150) String email_login,
        @NotBlank @Size(max = 255) String senha_hash,
        Boolean status_ativo
){
}