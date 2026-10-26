package chronos.tech.application.dto.notification;

import chronos.tech.domain.model.classes.EventoDestinatario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventoEmailData(
        Integer eventoId,
        String titulo,
        String descricao,
        String local,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        List<EventoDestinatario> destinatarios
) {
}
