package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.application.port.in.TurmaUseCase;
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
@RequestMapping("/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Endpoints para gerenciamento de turmas")
@SecurityRequirement(name = "bearerAuth")
public class TurmaController {

    private final TurmaUseCase service;

    @Operation(
            summary = "Listar todas as turmas",
            description = "Retorna todas as turmas cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> getAllTurmas() {
        return ResponseEntity.ok().body(service.getAllTurmas());
    }

    @Operation(
            summary = "Buscar turma por ID",
            description = "Retorna uma turma específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Turma encontrada",
            content = @Content(schema = @Schema(implementation = TurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Turma não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> getTurma(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getTurma(id));
    }

    @Operation(
            summary = "Criar turma",
            description = "Cria uma nova turma"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Turma criada com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<TurmaResponseDTO> saveTurma(
            @RequestBody @Validated TurmaRequestDTO turma){
        TurmaResponseDTO turmaCriada = service.saveTurma(turma);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaCriada);
    }

    @Operation(
            summary = "Atualizar turma",
            description = "Atualiza uma turma existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Turma atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Turma não encontrada",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<TurmaResponseDTO> updateTurma(
            @PathVariable @Validated Long id,
            @RequestBody TurmaRequestDTO turma){
        TurmaResponseDTO turmaAtualizada = service.updateTurma(id, turma);
        return ResponseEntity.ok().body(turmaAtualizada);
    }

    @Operation(
            summary = "Deletar turma",
            description = "Remove uma turma pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Turma removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Turma não encontrada",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTurma(
            @PathVariable @Validated Long id){
        service.deleteTurma(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<AlunoComPresencaResponseDTO>> alunosDaTurma(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getAlunosDaTurmaComPresenca(id));
    }

}