package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoRequestDTO(
        @NotBlank @Size(max = 200) String titulo,
        @NotNull LocalDate data_evento,
        @NotNull LocalTime hora_inicio_evento,
        @NotNull LocalTime hora_fim_evento,
        @NotNull Long id_categoria,
        @NotNull Long id_secretaria
) {
}
