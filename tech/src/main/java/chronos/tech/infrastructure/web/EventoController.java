package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;
import chronos.tech.application.port.in.EventoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Endpoints para gerenciamento de eventos")
public class EventoController {

    private final EventoUseCase service;

    @Operation(
            summary = "Listar todos os eventos",
            description = "Retorna todos os eventos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = EventoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> allEventos(){
        var resposta = service.getAllEventos();
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(
            summary = "Buscar evento por ID",
            description = "Retorna um evento específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Evento encontrado",
            content = @Content(schema = @Schema(implementation = EventoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Evento não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> getEvento(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getEvento(id));
    }

    @Operation(
            summary = "Criar evento",
            description = "Cria um novo evento"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Evento criado com sucesso",
            content = @Content(schema = @Schema(implementation = EventoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<EventoResponseDTO> saveEvento(
            @RequestBody @Validated EventoRequestDTO evento){
        EventoResponseDTO eventoCriado = service.saveEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
    }

    @Operation(
            summary = "Atualizar evento",
            description = "Atualiza os dados de um evento existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Evento atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = EventoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Evento não encontrado",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<EventoResponseDTO> updateEvento(
            @PathVariable @Validated Long id,
            @RequestBody EventoRequestDTO evento){
        EventoResponseDTO eventoAtualizado = service.updateEvento(id, evento);
        return ResponseEntity.ok().body(eventoAtualizado);
    }

    @Operation(
            summary = "Deletar evento",
            description = "Remove um evento pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Evento removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Evento não encontrado",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvento(
            @PathVariable @Validated Long id){
        service.deletEvento(id);
        return ResponseEntity.ok().build();
    }
}