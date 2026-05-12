package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.SecretariaRequestDTO;
import chronos.tech.application.dto.response.SecretariaResponseDTO;
import chronos.tech.application.port.in.SecretariaUseCase;
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
@RequestMapping("/secretarias")
@RequiredArgsConstructor
@Tag(name = "Secretarias", description = "Endpoints para gerenciamento de secretarias")
@SecurityRequirement(name = "bearerAuth")
public class SecretariaController {

    private final SecretariaUseCase service;

    @Operation(
            summary = "Listar todas as secretarias",
            description = "Retorna todas as secretarias cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = SecretariaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<SecretariaResponseDTO>> allSecretaria(){
        var resposta = service.getAllSecretaria();
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(
            summary = "Buscar secretaria por ID",
            description = "Retorna uma secretaria específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Secretaria encontrada",
            content = @Content(schema = @Schema(implementation = SecretariaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Secretaria não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<SecretariaResponseDTO> getSecretaria(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getSecretaria(id));
    }

    @Operation(
            summary = "Criar secretaria",
            description = "Cria uma nova secretaria"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Secretaria criada com sucesso",
            content = @Content(schema = @Schema(implementation = SecretariaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<SecretariaResponseDTO> saveSecretaria(
            @RequestBody @Validated SecretariaRequestDTO secretaria){
        SecretariaResponseDTO secretariaCriada = service.saveSecretaria(secretaria);
        return ResponseEntity.status(HttpStatus.CREATED).body(secretariaCriada);
    }

    @Operation(
            summary = "Atualizar secretaria",
            description = "Atualiza uma secretaria existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Secretaria atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = SecretariaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Secretaria não encontrada",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<SecretariaResponseDTO> updateSecretaria(
            @PathVariable @Validated Long id,
            @RequestBody SecretariaRequestDTO secretaria){
        SecretariaResponseDTO secretariaAtualizada = service.updateSecretaria(id, secretaria);
        return ResponseEntity.ok().body(secretariaAtualizada);
    }

    @Operation(
            summary = "Deletar secretaria",
            description = "Remove uma secretaria pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Secretaria removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Secretaria não encontrada",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSecretaria(
            @PathVariable @Validated Long id){
        service.deletSecretaria(id);
        return ResponseEntity.ok().build();
    }

}