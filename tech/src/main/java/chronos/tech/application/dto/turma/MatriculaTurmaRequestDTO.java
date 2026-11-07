package chronos.tech.application.dto.turma;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MatriculaTurmaRequestDTO(
        @NotNull Long id_turma,
        @NotNull Long id_pessoa,
        LocalDate data_matricula
) {
}
