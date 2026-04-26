package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.StatusTurma;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TurmaRequestDTO(
        @NotBlank @Size(max = 100) String nome_turma,
        @NotNull LocalDate data_inicio,
        LocalDate data_encerramento,
        StatusTurma status_turma
) {
}
