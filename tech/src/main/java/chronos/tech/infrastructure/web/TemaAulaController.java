package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TemaAulaRequestDTO;
import chronos.tech.application.dto.response.TemaAulaResponseDTO;
import chronos.tech.application.port.in.TemaAulaUseCase;
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
@RequestMapping("/temas-aula")
@RequiredArgsConstructor
@Tag(name = "Temas de Aula", description = "Endpoints para gerenciamento de temas de aula")
public class TemaAulaController {

    private final TemaAulaUseCase service;

    @Operation(
            summary = "Listar todos os temas de aula",
            description = "Retorna todos os temas de aula cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = TemaAulaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<TemaAulaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllTemas());
    }

    @Operation(
            summary = "Buscar tema de aula por ID",
            description = "Retorna um tema de aula específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tema encontrado",
            content = @Content(schema = @Schema(implementation = TemaAulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tema não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<TemaAulaResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getTema(id));
    }

    @Operation(
            summary = "Criar tema de aula",
            description = "Cria um novo tema de aula"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Tema criado com sucesso",
            content = @Content(schema = @Schema(implementation = TemaAulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<TemaAulaResponseDTO> create(
            @RequestBody @Validated TemaAulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTema(dto));
    }

    @Operation(
            summary = "Atualizar tema de aula",
            description = "Atualiza um tema de aula existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tema atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = TemaAulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tema não encontrado",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<TemaAulaResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody TemaAulaRequestDTO dto) {
        return ResponseEntity.ok(service.updateTema(id, dto));
    }

    @Operation(
            summary = "Deletar tema de aula",
            description = "Remove um tema de aula pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Tema removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tema não encontrado",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteTema(id);
        return ResponseEntity.noContent().build();
    }
}