package chronos.tech.application.dto.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoPessoaCadastroRequestDTO(
        @NotBlank @Size(max = 10) String cep,
        @NotBlank @Size(max = 150) String logradouro,
        @NotBlank @Size(max = 10) String numero,
        @Size(max = 100) String complemento,
        @NotBlank @Size(max = 100) String bairro,
        @NotBlank @Size(max = 100) String cidade,
        @NotBlank @Pattern(regexp = "^[A-Z]{2}$") String uf
) {
}
