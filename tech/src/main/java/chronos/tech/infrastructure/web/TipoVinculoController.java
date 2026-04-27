package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TipoVinculoRequestDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;
import chronos.tech.application.port.in.TipoVinculoUseCase;
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
@RequestMapping("/tipos-vinculo")
@RequiredArgsConstructor
@Tag(name = "Tipos de Vínculo", description = "Endpoints para gerenciamento de tipos de vínculo")
public class TipoVinculoController {

    private final TipoVinculoUseCase service;

    @Operation(
            summary = "Listar todos os tipos de vínculo",
            description = "Retorna todos os tipos de vínculo cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = TipoVinculoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<TipoVinculoResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllTiposVinculo());
    }

    @Operation(
            summary = "Buscar tipo de vínculo por ID",
            description = "Retorna um tipo de vínculo específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tipo encontrado",
            content = @Content(schema = @Schema(implementation = TipoVinculoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tipo não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<TipoVinculoResponseDTO> byId(
            @PathVariable @Validated Integer id) {
        return ResponseEntity.ok(service.getTipoVinculo(id));
    }

    @Operation(
            summary = "Criar tipo de vínculo",
            description = "Cria um novo tipo de vínculo"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Tipo criado com sucesso",
            content = @Content(schema = @Schema(implementation = TipoVinculoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<TipoVinculoResponseDTO> create(
            @RequestBody @Validated TipoVinculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTipoVinculo(dto));
    }

    @Operation(
            summary = "Atualizar tipo de vínculo",
            description = "Atualiza um tipo de vínculo existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tipo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = TipoVinculoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tipo não encontrado",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<TipoVinculoResponseDTO> update(
            @PathVariable @Validated Integer id,
            @RequestBody TipoVinculoRequestDTO dto) {
        return ResponseEntity.ok(service.updateTipoVinculo(id, dto));
    }

    @Operation(
            summary = "Deletar tipo de vínculo",
            description = "Remove um tipo de vínculo pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Tipo removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tipo não encontrado",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Integer id) {
        service.deleteTipoVinculo(id);
        return ResponseEntity.noContent().build();
    }
}