package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;

import java.util.List;

public interface EventoUseCase {
    List<EventoResponseDTO> getAllEventos();

    EventoResponseDTO getEvento(Long id);

    EventoResponseDTO saveEvento(EventoRequestDTO evento);

    EventoResponseDTO updateEvento(Long id, EventoRequestDTO eventoAtualizado);

    void deletEvento(Long id);
}
