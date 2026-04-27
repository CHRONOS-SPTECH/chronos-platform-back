package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.UsuarioPerfilRequestDTO;
import chronos.tech.application.dto.response.UsuarioPerfilResponseDTO;
import chronos.tech.application.port.in.UsuarioPerfilUseCase;
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
@RequestMapping("/usuarios-perfis")
@RequiredArgsConstructor
@Tag(name = "Usuários x Perfis", description = "Endpoints para gerenciamento de vínculos entre usuários e perfis")
public class UsuarioPerfilController {

    private final UsuarioPerfilUseCase service;

    @Operation(
            summary = "Listar todos os vínculos",
            description = "Retorna todos os vínculos entre usuários e perfis"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioPerfilResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<UsuarioPerfilResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllVinculos());
    }

    @Operation(
            summary = "Buscar vínculo por ID",
            description = "Retorna um vínculo específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vínculo encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioPerfilResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vínculo não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPerfilResponseDTO> byId(
            @PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getVinculo(id));
    }

    @Operation(
            summary = "Criar vínculo",
            description = "Cria um novo vínculo entre usuário e perfil"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Vínculo criado com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioPerfilResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<UsuarioPerfilResponseDTO> create(
            @RequestBody @Validated UsuarioPerfilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveVinculo(dto));
    }

    @Operation(
            summary = "Atualizar vínculo",
            description = "Atualiza um vínculo existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vínculo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = UsuarioPerfilResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vínculo não encontrado",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioPerfilResponseDTO> update(
            @PathVariable @Validated Long id,
            @RequestBody UsuarioPerfilRequestDTO dto) {
        return ResponseEntity.ok(service.updateVinculo(id, dto));
    }

    @Operation(
            summary = "Deletar vínculo",
            description = "Remove um vínculo pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Vínculo removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vínculo não encontrado",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Long id) {
        service.deleteVinculo(id);
        return ResponseEntity.noContent().build();
    }
}