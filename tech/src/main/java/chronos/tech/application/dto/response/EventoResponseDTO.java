package chronos.tech.application.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventoResponseDTO(
        Integer id_evento,
        String titulo,
        LocalDate data_evento,
        LocalTime hora_inicio_evento,
        LocalTime hora_fim_evento,
        Integer id_categoria,
        Integer id_secretaria,
        List<ParticipanteDTO> participantes
) {
    public record ParticipanteDTO(
            Integer id_pessoa,
            String nome,
            String email,
            Boolean compareceu
    ) {
    }
}