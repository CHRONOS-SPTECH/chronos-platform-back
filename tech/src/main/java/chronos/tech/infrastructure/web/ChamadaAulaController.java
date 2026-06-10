package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.request.ListaChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.application.port.in.ChamadaAulaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/chamadas-aula")
@RequiredArgsConstructor
@Tag(name = "Chamadas de Aula", description = "Endpoints para gerenciamento de chamadas de aula")
@SecurityRequirement(name = "bearerAuth")
public class ChamadaAulaController {

    private final ChamadaAulaUseCase service;

    @Operation(
            summary = "Listar todas as chamadas",
            description = "Retorna todas as chamadas de aula cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = ChamadaAulaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<ChamadaAulaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllChamadas());
    }

    @Operation(
            summary = "Buscar chamada por ID",
            description = "Retorna uma chamada de aula específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Chamada encontrada",
            content = @Content(schema = @Schema(implementation = ChamadaAulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Chamada não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<ChamadaAulaResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getChamada(id));
    }

    @Operation(
            summary = "Criar chamada de aula",
            description = "Cria uma nova chamada de aula"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Chamada criada com sucesso",
            content = @Content(schema = @Schema(implementation = ChamadaAulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<ChamadaAulaResponseDTO> create(
            @RequestBody @Validated ChamadaAulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveChamada(dto));
    }

    @Operation(
            summary = "Atualizar chamada de aula",
            description = "Atualiza uma chamada de aula existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Chamada atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = ChamadaAulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Chamada não encontrada",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<ChamadaAulaResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody ChamadaAulaRequestDTO dto) {
        return ResponseEntity.ok(service.updateChamada(id, dto));
    }

    @Operation(
            summary = "Deletar chamada de aula",
            description = "Remove uma chamada de aula pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Chamada removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Chamada não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteChamada(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Criar chamada em lote",
            description = "Cria a chamada de aula para múltiplos alunos de uma vez"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Chamadas criadas com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChamadaAulaResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping("/em-lote")
    public ResponseEntity<List<ChamadaAulaResponseDTO>> createEmLote(
            @RequestBody @Validated ListaChamadaAulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveListaChamada(dto));
    }

    @GetMapping("/aula/{id_aula}")
    public ResponseEntity<List<ChamadaAulaResponseDTO>> byAula(
            @PathVariable @Validated Long id_aula) {
        return ResponseEntity.ok(service.getChamadasByAula(id_aula));
    }
}