package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.request.MovimentacaoAulaDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaComInstrutorResponseDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaResponseDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.dto.response.RelatorioImportacaoResponseDTO;
import chronos.tech.application.port.in.AulaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/aulas")
@RequiredArgsConstructor
@Tag(name = "Aulas", description = "Endpoints para gerenciamento de aulas")
@SecurityRequirement(name = "bearerAuth")
public class AulaController {

    private final AulaUseCase service;

    @Operation(
            summary = "Listar todas as aulas",
            description = "Retorna todas as aulas cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = AulaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllAulas());
    }


    @Operation(
            summary = "Buscar aula por ID",
            description = "Retorna uma aula específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aula encontrada",
            content = @Content(schema = @Schema(implementation = AulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> byId(
            @PathVariable @Validated Integer id) {
        return ResponseEntity.ok(service.getAula(id));
    }

    @Operation(
            summary = "Criar aula",
            description = "Cria uma nova aula"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Aula criada com sucesso",
            content = @Content(schema = @Schema(implementation = AulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<AulaResponseDTO> create(
            @RequestBody @Validated AulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAula(dto));
    }

    @Operation(
            summary = "Atualizar aula",
            description = "Atualiza uma aula existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aula atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = AulaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula não encontrada",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> update(
            @PathVariable @Validated Integer id,
            @RequestBody AulaRequestDTO dto) {
        return ResponseEntity.ok(service.updateAula(id, dto));
    }

    @Operation(
            summary = "Deletar aula",
            description = "Remove uma aula pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Aula removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Integer id) {
        service.deleteAula(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Buscar aulas",
            description = "Busca as aulas do dia"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aulas achadas",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aulas não encontradas",
            content = @Content
    )
    @GetMapping("/dia")
    public ResponseEntity<List<AulaComTemaEMateriaResponseDTO>> aulasDoDia(
            @RequestParam
            @Validated
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam @Validated Integer instrutorId) {

        Date dataSql = Date.valueOf(data);

        return ResponseEntity.ok(service.getAulasDoDia(dataSql, instrutorId));
    }

    @Operation(
            summary = "Buscar detalhes da aula",
            description = "Busca as aulas com mais detalhes de informação"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Detalhes das aulas achada com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Detalhes das aulas não encontrada",
            content = @Content
    )
    @GetMapping("/detalhadas")
    public ResponseEntity<List<AulaComTemaEMateriaComInstrutorResponseDTO>> getAllAulasDetails() {
        return ResponseEntity.ok(service.getAllAulasDetails());
    }

    @Operation(
            summary = "Buscar aula com detalhes por ID",
            description = "Busca os detalhes da aula por ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aula achada com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula não encontrada",
            content = @Content
    )
    @GetMapping("/{id}/detalhada")
    public ResponseEntity<AulaComTemaEMateriaResponseDTO> getAulaDetalhada(@PathVariable Integer id) {
        AulaComTemaEMateriaResponseDTO dto = service.getAulaComTemaEMateriaPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Importar cronograma de aulas",
            description = "Importa o cronograma de aulas a partir de um arquivo Excel"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Aula importada com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula não importada",
            content = @Content
    )
    @PostMapping(value = "/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RelatorioImportacaoResponseDTO> importarPlanilha(
            @RequestParam("file") MultipartFile file) {

        RelatorioImportacaoResponseDTO relatorio = service.importarCronograma(file);

        return ResponseEntity.ok(relatorio);
    }

    @Operation(
            summary = "Buscar aulas por turma",
            description = "Busca aulas por turma através do ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aula da turma achada com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula da turma não encontrada",
            content = @Content
    )
    @GetMapping("turma/{id}")
    public ResponseEntity<List<AulaComTemaEMateriaComInstrutorResponseDTO>> getAulasPorTurma(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getAulasPorTurma(id));
    }

    @Operation(
            summary = "Remanejar aulas",
            description = "Remaneja as aulas por lote"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Aula remanejada com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aula não remanejada",
            content = @Content
    )
    @PatchMapping("/remanejar")
    public ResponseEntity<Void> remanejarAulasEmLote(
            @RequestBody List<MovimentacaoAulaDTO> movimentacoes) {

        service.remanejarAulasEmLote(movimentacoes);
        return ResponseEntity.noContent().build();
    }
}