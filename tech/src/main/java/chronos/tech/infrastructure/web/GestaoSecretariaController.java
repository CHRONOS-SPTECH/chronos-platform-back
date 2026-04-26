package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.GestaoSecretariaRequestDTO;
import chronos.tech.application.dto.response.GestaoSecretariaResponseDTO;
import chronos.tech.application.port.in.GestaoSecretariaUseCase;
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
@RequestMapping("/gestoes-secretaria")
@RequiredArgsConstructor
@Tag(name = "Gestão de Secretaria", description = "Endpoints para gerenciamento de gestões da secretaria")
public class GestaoSecretariaController {

    private final GestaoSecretariaUseCase service;

    @Operation(
            summary = "Listar todas as gestões",
            description = "Retorna todas as gestões cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = GestaoSecretariaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<GestaoSecretariaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllGestoes());
    }

    @Operation(
            summary = "Buscar gestão por ID",
            description = "Retorna uma gestão específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gestão encontrada",
            content = @Content(schema = @Schema(implementation = GestaoSecretariaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Gestão não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<GestaoSecretariaResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getGestao(id));
    }

    @Operation(
            summary = "Criar gestão",
            description = "Cria uma nova gestão de secretaria"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Gestão criada com sucesso",
            content = @Content(schema = @Schema(implementation = GestaoSecretariaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<GestaoSecretariaResponseDTO> create(
            @RequestBody @Validated GestaoSecretariaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveGestao(dto));
    }

    @Operation(
            summary = "Atualizar gestão",
            description = "Atualiza uma gestão existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gestão atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = GestaoSecretariaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Gestão não encontrada",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<GestaoSecretariaResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody GestaoSecretariaRequestDTO dto) {
        return ResponseEntity.ok(service.updateGestao(id, dto));
    }

    @Operation(
            summary = "Deletar gestão",
            description = "Remove uma gestão pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Gestão removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Gestão não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteGestao(id);
        return ResponseEntity.noContent().build();
    }
}