package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;
import chronos.tech.application.port.in.MatriculaTurmaUseCase;
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
@RequestMapping("/matriculas-turma")
@RequiredArgsConstructor
@Tag(name = "Matrículas em Turma", description = "Endpoints para gerenciamento de matrículas em turmas")
public class MatriculaTurmaController {

    private final MatriculaTurmaUseCase service;

    @Operation(
            summary = "Listar todas as matrículas",
            description = "Retorna todas as matrículas em turma cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = MatriculaTurmaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<MatriculaTurmaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllMatriculas());
    }

    @Operation(
            summary = "Buscar matrícula por ID",
            description = "Retorna uma matrícula específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Matrícula encontrada",
            content = @Content(schema = @Schema(implementation = MatriculaTurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Matrícula não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaTurmaResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getMatricula(id));
    }

    @Operation(
            summary = "Criar matrícula",
            description = "Cria uma nova matrícula em turma"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Matrícula criada com sucesso",
            content = @Content(schema = @Schema(implementation = MatriculaTurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<MatriculaTurmaResponseDTO> create(
            @RequestBody @Validated MatriculaTurmaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveMatricula(dto));
    }

    @Operation(
            summary = "Atualizar matrícula",
            description = "Atualiza uma matrícula existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Matrícula atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = MatriculaTurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Matrícula não encontrada",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<MatriculaTurmaResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody MatriculaTurmaRequestDTO dto) {
        return ResponseEntity.ok(service.updateMatricula(id, dto));
    }

    @Operation(
            summary = "Deletar matrícula",
            description = "Remove uma matrícula pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Matrícula removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Matrícula não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteMatricula(id);
        return ResponseEntity.noContent().build();
    }
}