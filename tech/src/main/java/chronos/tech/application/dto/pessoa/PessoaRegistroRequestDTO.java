package chronos.tech.application.dto.pessoa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public record PessoaRegistroRequestDTO(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(max = 20) String telefone,
        @Size(max = 25) String genero,
        @Pattern(regexp = "^\\d{11}$") String cpf,
        Boolean bolsista,
        @NotNull Integer tipo_vinculo_id,
        LocalDate data_nascimento,
        LocalDate data_ingresso,
        LocalDate data_membro,
        LocalDate data_saida,
        @Size(max = 14_000_000) String imagemPerfilBase64,
        @Valid EnderecoPessoaCadastroRequestDTO endereco,
        @Size(min = 1, max = 512) List<Double> vetorBiometrico
) {
}
