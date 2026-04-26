package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoPessoaRequestDTO(
        @NotBlank @Size(max = 10)
        String cep,
        @NotBlank @Size(max = 150)
        String logradouro,
        @NotBlank @Size(max = 10)
        String numero,
        @Size(max = 100)
        String complemento,
        @NotBlank @Size(max = 100)
        String bairro,
        @NotBlank @Size(max = 100)
        String cidade,
        @NotBlank
        @Pattern(regexp = "^[A-Z]{2}$")
        String uf,
        @NotNull
        Long id_pessoa
) {
}
