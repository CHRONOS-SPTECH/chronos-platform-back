package chronos.tech.application.dto.pessoa;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PessoaBiometriaRequestDTO(
        @NotEmpty @Size(max = 512) List<@NotNull Double> vetorBiometrico,
        @Size(max = 14_000_000) String imagemPerfilBase64
) {
}
