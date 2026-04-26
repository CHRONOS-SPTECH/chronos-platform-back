package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.StatusMateria;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record HistoricoAcademicoRequestDTO(
        @NotNull Long id_pessoa,
        @NotNull Long id_materia,
        @NotNull StatusMateria status_materia,
        @NotNull LocalDate data_status
) {
}
