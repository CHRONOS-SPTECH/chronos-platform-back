package chronos.tech.application.dto.turma;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record DecisaoFormacaoRequestDTO(
        @NotNull Boolean aprovado,
        @Size(max = 500) String observacao
) {}
