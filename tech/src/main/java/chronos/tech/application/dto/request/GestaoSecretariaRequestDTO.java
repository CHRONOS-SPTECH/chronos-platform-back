package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.CargoAcesso;
import jakarta.validation.constraints.NotNull;

public record GestaoSecretariaRequestDTO(
        @NotNull Long id_pessoa,
        @NotNull Long id_secretaria,
        @NotNull CargoAcesso cargo_acesso
) {
}
