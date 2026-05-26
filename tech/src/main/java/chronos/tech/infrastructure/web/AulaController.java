package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.AulaRequestDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/aulas")
@RequiredArgsConstructor
@Tag(name = "Aulas", description = "Endpoints para gerenciamento de aulas")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*" )
public class AulaController {

    // Aqui nós injetamos a PORTA DE ENTRADA (Use Case), nunca o serviço direto!
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

    @PostMapping(value = "/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Importa o cronograma de aulas a partir de um arquivo Excel")
    public ResponseEntity<RelatorioImportacaoResponseDTO> importarPlanilha(
            @RequestParam("file") MultipartFile file) {

        // Chama o caso de uso passando o arquivo bruto
        RelatorioImportacaoResponseDTO relatorio = service.importarCronograma(file);

        // Retorna HTTP 200 com o resumo de tudo o que aconteceu
        return ResponseEntity.ok(relatorio);
    }
}