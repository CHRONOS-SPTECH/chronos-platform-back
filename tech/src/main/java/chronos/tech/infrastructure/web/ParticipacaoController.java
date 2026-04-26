package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.ParticipacaoRequestDTO;
import chronos.tech.application.dto.response.ParticipacaoResponseDTO;
import chronos.tech.application.port.in.ParticipacaoUseCase;
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
@RequestMapping("/participacoes")
@RequiredArgsConstructor
@Tag(name = "Participações", description = "Endpoints para gerenciamento de participações")
public class ParticipacaoController {

    private final ParticipacaoUseCase service;

    @Operation(
            summary = "Listar todas as participações",
            description = "Retorna todas as participações cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = ParticipacaoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<ParticipacaoResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllParticipacoes());
    }

    @Operation(
            summary = "Buscar participação por ID",
            description = "Retorna uma participação específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Participação encontrada",
            content = @Content(schema = @Schema(implementation = ParticipacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Participação não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<ParticipacaoResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getParticipacao(id));
    }

    @Operation(
            summary = "Criar participação",
            description = "Cria uma nova participação"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Participação criada com sucesso",
            content = @Content(schema = @Schema(implementation = ParticipacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<ParticipacaoResponseDTO> create(
            @RequestBody @Validated ParticipacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveParticipacao(dto));
    }

    @Operation(
            summary = "Atualizar participação",
            description = "Atualiza uma participação existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Participação atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = ParticipacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Participação não encontrada",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<ParticipacaoResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody ParticipacaoRequestDTO dto) {
        return ResponseEntity.ok(service.updateParticipacao(id, dto));
    }

    @Operation(
            summary = "Deletar participação",
            description = "Remove uma participação pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Participação removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Participação não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteParticipacao(id);
        return ResponseEntity.noContent().build();
    }
}