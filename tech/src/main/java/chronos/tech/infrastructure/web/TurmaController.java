package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.turma.TurmaRequestDTO;
import chronos.tech.application.dto.turma.AvaliacaoFormacaoResponseDTO;
import chronos.tech.application.dto.turma.DecisaoFormacaoRequestDTO;
import chronos.tech.application.dto.turma.AlunoDisponivelMatriculaResponseDTO;
import chronos.tech.application.dto.turma.TurmaDetalhesResponseDTO;
import chronos.tech.application.dto.turma.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.pessoa.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.turma.TurmaResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import chronos.tech.application.dto.turma.TurmaAlunoPaginaResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Endpoints para gerenciamento de turmas")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SECRETÁRIO')")
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

    @GetMapping(params = "page")
    public ResponseEntity<Page<TurmaResponseDTO>> getTurmasPaginadas(
            @PageableDefault(size = 20, sort = "nomeTurma") Pageable pageable) {
        return ResponseEntity.ok(service.getAllTurmas(pageable));
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

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<TurmaDetalhesResponseDTO> detalhes(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDetalhes(id));
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
    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> updateTurma(
            @PathVariable @Validated Long id,
            @RequestBody TurmaRequestDTO turma){
        TurmaResponseDTO turmaAtualizada = service.updateTurma(id, turma);
        return ResponseEntity.ok().body(turmaAtualizada);
    }

    @Operation(
            summary = "Desativar turma",
            description = "Muda o status da turma para inativa"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Turma desativada com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaDeletadaResponseDTO.class
            )))
    @ApiResponse(
            responseCode = "404",
            description = "Turma não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<TurmaDeletadaResponseDTO> deleteTurma(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.deleteTurma(id));
    }

    @PatchMapping("/{id}/inativacao")
    public ResponseEntity<TurmaDeletadaResponseDTO> inativarTurma(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteTurma(id));
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<TurmaDeletadaResponseDTO> iniciarTurma(@PathVariable Long id) {
        return ResponseEntity.ok(service.iniciarTurma(id));
    }

    @Operation(
            summary = "Encerrar turma",
            description = "Muda o status da turma para concluída"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Turma encerrada com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaDeletadaResponseDTO.class)
            ))
    @ApiResponse(
            responseCode = "404",
            description = "Turma não encontrada",
            content = @Content
    )
    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<TurmaDeletadaResponseDTO> encerrarTurma(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.encerrarTurma(id));
    }

    @GetMapping("/{id}/formandos")
    public ResponseEntity<Page<AvaliacaoFormacaoResponseDTO>> formandos(
            @PathVariable Long id,
            @PageableDefault(size = 20, sort = "pessoa.nome") Pageable pageable) {
        return ResponseEntity.ok(service.listarFormandos(id, pageable));
    }

    @PatchMapping("/{id}/formandos/{idPessoa}/decisao")
    public ResponseEntity<AvaliacaoFormacaoResponseDTO> decidirFormacao(
            @PathVariable Long id,
            @PathVariable Long idPessoa,
            @RequestBody @Validated DecisaoFormacaoRequestDTO decisao) {
        return ResponseEntity.ok(service.decidirFormacao(id, idPessoa, decisao));
    }

    @Operation(
            summary = "Buscar alunos por turma",
            description = "Retorna alunos com base no ID da Turma"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Alunos da turma encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Alunos da turma não encontrada",
            content = @Content
    )
    @GetMapping("/{id}/alunos")
    public ResponseEntity<Page<AlunoComPresencaResponseDTO>> alunosDaTurma(
            @PathVariable Integer id,
            @PageableDefault(size = 20, sort = "pessoa.nome") Pageable pageable) {
        return ResponseEntity.ok(service.getAlunosDaTurmaComPresenca(id, pageable));
    }

    @Operation(summary = "Listar alunos disponíveis para matrícula",
            description = "Pessoas ativas com vínculo elegível que ainda não estão matriculadas nesta turma")
    @GetMapping("/{id}/alunos-disponiveis")
    public ResponseEntity<Page<AlunoDisponivelMatriculaResponseDTO>> alunosDisponiveisParaMatricula(
            @PathVariable Long id,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(service.getAlunosDisponiveis(id, pageable));
    }

    @Operation(
            summary = "Buscar alunos por turma",
            description = "Retorna alunos com base no ID da Turma"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Alunos da turma encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = TurmaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Alunos da turma não encontrada",
            content = @Content
    )
    @GetMapping("/{id}/alunosPorTurma")
    public ResponseEntity<TurmaAlunoPaginaResponseDTO> alunosPorTurma(
            @PathVariable Long id,
            @PageableDefault(size = 20, sort = "pessoa.nome") Pageable pageable) {
        return ResponseEntity.ok(service.getAlunosByTurma(id, pageable));
    }

}
