package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.port.in.PessoaUseCase;
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
@RequestMapping("/pessoas")
@RequiredArgsConstructor
@Tag(name = "Pessoas", description = "Endpoints para gerenciamento de pessoas")
@SecurityRequirement(name = "bearerAuth")
public class PessoaController {

    private final PessoaUseCase service;

    @Operation(
            summary = "Listar todas as pessoas",
            description = "Retorna todas as pessoas cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> getAllPersons() {
        return ResponseEntity.ok().body(service.getAllPersons());
    }

    @Operation(
            summary = "Buscar pessoa por ID",
            description = "Retorna uma pessoa específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pessoa encontrada",
            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pessoa não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> getPessoa(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok(service.pegarPorId(id));
    }

    @Operation(
            summary = "Criar pessoa",
            description = "Cria uma nova pessoa"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Pessoa criada com sucesso",
            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<PessoaResponseDTO> savePessoa(
            @RequestBody @Validated PessoaRequestDTO pessoaRequestDto){
        PessoaResponseDTO pessoaResponseDto = service.createPessoa(pessoaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDto);
    }

    @Operation(
            summary = "Atualizar pessoa",
            description = "Atualiza uma pessoa existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pessoa atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pessoa não encontrada",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(
            @PathVariable @Validated Long id,
            @RequestBody PessoaRequestDTO pessoa){
        PessoaResponseDTO pessoaAtualizada = service.updatePessoa(id, pessoa);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @Operation(
            summary = "Deletar pessoa",
            description = "Remove uma pessoa pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pessoa removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pessoa não encontrada",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePessoa(
            @PathVariable @Validated Long id){
        service.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}