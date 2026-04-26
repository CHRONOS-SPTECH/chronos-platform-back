package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.HistoricoAcademicoRequestDTO;
import chronos.tech.application.dto.response.HistoricoAcademicoResponseDTO;
import chronos.tech.application.port.in.HistoricoAcademicoUseCase;
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
@RequestMapping("/historicos-academicos")
@RequiredArgsConstructor
@Tag(name = "Histórico Acadêmico", description = "Endpoints para gerenciamento de históricos acadêmicos")
public class HistoricoAcademicoController {

    private final HistoricoAcademicoUseCase service;

    @Operation(
            summary = "Listar todos os históricos",
            description = "Retorna todos os históricos acadêmicos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = HistoricoAcademicoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<HistoricoAcademicoResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllHistoricos());
    }

    @Operation(
            summary = "Buscar histórico por ID",
            description = "Retorna um histórico acadêmico específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Histórico encontrado",
            content = @Content(schema = @Schema(implementation = HistoricoAcademicoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Histórico não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoAcademicoResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getHistorico(id));
    }

    @Operation(
            summary = "Criar histórico",
            description = "Cria um novo histórico acadêmico"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Histórico criado com sucesso",
            content = @Content(schema = @Schema(implementation = HistoricoAcademicoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<HistoricoAcademicoResponseDTO> create(
            @RequestBody @Validated HistoricoAcademicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveHistorico(dto));
    }

    @Operation(
            summary = "Atualizar histórico",
            description = "Atualiza um histórico acadêmico existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Histórico atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = HistoricoAcademicoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Histórico não encontrado",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoAcademicoResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody HistoricoAcademicoRequestDTO dto) {
        return ResponseEntity.ok(service.updateHistorico(id, dto));
    }

    @Operation(
            summary = "Deletar histórico",
            description = "Remove um histórico acadêmico pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Histórico removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Histórico não encontrado",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteHistorico(id);
        return ResponseEntity.noContent().build();
    }
}