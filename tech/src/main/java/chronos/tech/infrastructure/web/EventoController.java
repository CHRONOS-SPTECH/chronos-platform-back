package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;
import chronos.tech.application.port.in.EventoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoUseCase service;

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> allEventos(){
        var resposta = service.getAllEventos();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> getEvento(@PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getEvento(id));
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> saveEvento(@RequestBody @Validated EventoRequestDTO evento){
        EventoResponseDTO eventoCriado = service.saveEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
    }

    @PutMapping("{id}")
    public ResponseEntity<EventoResponseDTO> updateEvento(@PathVariable @Validated Long id, @RequestBody EventoRequestDTO evento){
        EventoResponseDTO eventoAtualizado = service.updateEvento(id, evento);
        return ResponseEntity.ok().body(eventoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable @Validated Long id){
        service.deletEvento(id);
        return ResponseEntity.ok().build();
    }
}
