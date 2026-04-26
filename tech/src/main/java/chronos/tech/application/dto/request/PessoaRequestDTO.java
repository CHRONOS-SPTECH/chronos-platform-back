package chronos.tech.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PessoaRequestDTO(
        @NotBlank @Size(max = 150)
        String nome,
        @NotBlank @Email @Size(max = 255)
        String email,
        @NotBlank @Size(max = 20)
        String telefone,
        @Size(max = 25)
        String genero,
        @Pattern(regexp = "^\\d{11}$")
        String cpf,
        Boolean bolsista,
        @Size(max = 512)
        String url_foto_perfil,
        @NotNull
        Integer tipo_vinculo_id,
        LocalDate data_nascimento,
        LocalDate data_ingresso,
        LocalDate data_membro,
        LocalDate data_saida
) {
}
