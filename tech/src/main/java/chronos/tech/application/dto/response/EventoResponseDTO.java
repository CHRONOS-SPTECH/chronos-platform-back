package chronos.tech.application.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponseDTO(
        Integer id_evento,
        String titulo,
        LocalDate data_evento,
        LocalTime hora_inicio_evento,
        LocalTime hora_fim_evento,
        Integer id_categoria,
        Integer id_secretaria
) {
}
