package chronos.tech.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventoRequestDTO(
        @NotBlank @Size(max = 200) String titulo,
        @NotNull LocalDate data_evento,
        @NotNull LocalTime hora_inicio_evento,
        @NotNull LocalTime hora_fim_evento,
        @NotNull Long id_categoria,
        @NotNull Long id_secretaria,
        String descricao,
        String local_evento,
        List<@NotNull Long> id_turmas,
        Boolean incluir_publico_externo
) {
    public EventoRequestDTO {
        id_turmas = id_turmas == null ? List.of() : List.copyOf(id_turmas);
        incluir_publico_externo = incluir_publico_externo != null && incluir_publico_externo;
    }
}
