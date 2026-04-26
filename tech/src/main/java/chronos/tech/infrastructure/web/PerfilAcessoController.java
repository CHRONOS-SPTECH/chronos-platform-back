package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.response.PerfilAcessoResponseDTO;
import chronos.tech.application.port.in.PerfilAcessoUseCase;
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
@RequestMapping("/perfisAcesso")
@RequiredArgsConstructor
@Tag(name = "Perfis de Acesso", description = "Endpoints para gerenciamento de perfis de acesso")
public class PerfilAcessoController {

    private final PerfilAcessoUseCase service;

    @Operation(
            summary = "Listar todos os perfis de acesso",
            description = "Retorna todos os perfis de acesso cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PerfilAcessoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<PerfilAcessoResponseDTO>> allPerfilAcesso(){
        var resposta = service.getAllPerfilAcesso();
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(
            summary = "Buscar perfil de acesso por ID",
            description = "Retorna um perfil de acesso específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Perfil encontrado",
            content = @Content(schema = @Schema(implementation = PerfilAcessoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Perfil não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<PerfilAcessoResponseDTO> getPerfilAcesso(
            @PathVariable @Validated Long id){
        var resposta = service.getPerfilAcesso(id);
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(
            summary = "Criar perfil de acesso",
            description = "Cria um novo perfil de acesso"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Perfil criado com sucesso",
            content = @Content(schema = @Schema(implementation = PerfilAcessoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<PerfilAcessoResponseDTO> savePerfilAcesso(
            @RequestBody @Validated PerfilAcessoRequestDTO perfilAcesso){
        PerfilAcessoResponseDTO novoPerfil = service.savePerfilAcesso(perfilAcesso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPerfil);
    }

    @Operation(
            summary = "Atualizar perfil de acesso",
            description = "Atualiza um perfil de acesso existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Perfil atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = PerfilAcessoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Perfil não encontrado",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<PerfilAcessoResponseDTO> updatePerfilAcesso(
            @PathVariable @Validated Long id,
            @RequestBody PerfilAcessoRequestDTO perfilAcesso){
        PerfilAcessoResponseDTO perfilAtualizado = service.updatePerfilAcesso(id, perfilAcesso);
        return ResponseEntity.ok(perfilAtualizado);
    }

    @Operation(
            summary = "Deletar perfil de acesso",
            description = "Remove um perfil de acesso pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Perfil removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Perfil não encontrado",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePerfilAcesso(
            @PathVariable @Validated Long id){
        service.deletPerfilAcesso(id);
        return ResponseEntity.ok().build();
    }

}