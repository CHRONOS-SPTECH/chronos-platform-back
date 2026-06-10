package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.NivelFormacaoRequestDTO;
import chronos.tech.application.dto.response.NivelFormacaoResponseDTO;
import chronos.tech.application.port.in.NivelFormacaoUseCase;
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
@RequestMapping("/niveis-formacao")
@RequiredArgsConstructor
@Tag(name = "Níveis de Formação", description = "Endpoints para gerenciamento de níveis de formação")
@SecurityRequirement(name = "bearerAuth")
public class NivelFormacaoController {

    private final NivelFormacaoUseCase service;

    @Operation(
            summary = "Listar todos os níveis de formação",
            description = "Retorna todos os níveis de formação cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = NivelFormacaoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<NivelFormacaoResponseDTO>> allNivelFormacao(){
        var resposta = service.getAllNivelFormacao();
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(
            summary = "Buscar nível de formação por ID",
            description = "Retorna um nível de formação específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Nível encontrado",
            content = @Content(schema = @Schema(implementation = NivelFormacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Nível não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<NivelFormacaoResponseDTO> getNivelFormacao(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getNivelFormacao(id));
    }

    @Operation(
            summary = "Criar nível de formação",
            description = "Cria um novo nível de formação"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Nível criado com sucesso",
            content = @Content(schema = @Schema(implementation = NivelFormacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<NivelFormacaoResponseDTO> saveNivelFormacao(
            @RequestBody @Validated NivelFormacaoRequestDTO nivelFormacao){
        NivelFormacaoResponseDTO nivelCriado = service.saveNivelFormacao(nivelFormacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(nivelCriado);
    }

    @Operation(
            summary = "Atualizar nível de formação",
            description = "Atualiza um nível de formação existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Nível atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = NivelFormacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Nível não encontrado",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<NivelFormacaoResponseDTO> updateNivelFormacao(
            @PathVariable @Validated Long id,
            @RequestBody NivelFormacaoRequestDTO nivelFormacao){
        NivelFormacaoResponseDTO nivelAtualizado = service.updateNivelFormacao(id, nivelFormacao);
        return ResponseEntity.ok().body(nivelAtualizado);
    }

    @Operation(
            summary = "Deletar nível de formação",
            description = "Remove um nível de formação pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Nível removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Nível não encontrado",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNivelFormacao(
            @PathVariable @Validated Long id){
        service.deletNivelFormacao(id);
        return ResponseEntity.ok().build();
    }

}