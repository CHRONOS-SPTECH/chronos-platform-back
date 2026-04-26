package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaTurmaRequestDTO(
        @NotNull Long id_turma,
        @NotNull Long id_pessoa,
        @NotNull LocalDate data_matricula
) {
}
