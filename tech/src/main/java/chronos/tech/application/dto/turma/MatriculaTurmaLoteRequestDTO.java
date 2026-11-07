package chronos.tech.application.dto.turma;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record MatriculaTurmaLoteRequestDTO(
        @NotNull Long id_turma,
        @NotEmpty @Size(max = 200) List<@NotNull Long> ids_pessoas,
        LocalDate data_matricula
) {}
