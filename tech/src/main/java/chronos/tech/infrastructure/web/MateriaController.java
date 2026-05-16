package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.MateriaRequestDTO;
import chronos.tech.application.dto.response.MateriaResponseDTO;
import chronos.tech.application.port.in.MateriaUseCase;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/materias")
@RequiredArgsConstructor
@Tag(name = "Matérias", description = "Endpoints para gerenciamento de matérias")
@SecurityRequirement(name = "bearerAuth")
public class MateriaController {

    private final MateriaUseCase service;

    @Operation(
            summary = "Listar todas as matérias",
            description = "Retorna todas as matérias cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = MateriaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<MateriaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllMaterias());
    }

    @Operation(
            summary = "Buscar matéria por ID",
            description = "Retorna uma matéria específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Matéria encontrada",
            content = @Content(schema = @Schema(implementation = MateriaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Matéria não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getMateria(id));
    }

    @Operation(
            summary = "Criar matéria",
            description = "Cria uma nova matéria"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Matéria criada com sucesso",
            content = @Content(schema = @Schema(implementation = MateriaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<MateriaResponseDTO> create(
            @RequestBody @Validated MateriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveMateria(dto));
    }

    @Operation(
            summary = "Atualizar matéria",
            description = "Atualiza uma matéria existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Matéria atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = MateriaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Matéria não encontrada",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody MateriaRequestDTO dto) {
        return ResponseEntity.ok(service.updateMateria(id, dto));
    }

    @Operation(
            summary = "Deletar matéria",
            description = "Remove uma matéria pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Matéria removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Matéria não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteMateria(id);
        return ResponseEntity.noContent().build();
    }
}