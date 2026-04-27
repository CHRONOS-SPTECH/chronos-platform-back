package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.EnderecoPessoaRequestDTO;
import chronos.tech.application.dto.response.EnderecoPessoaResponseDTO;
import chronos.tech.application.port.in.EnderecoPessoaUseCase;
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
@RequestMapping("/enderecos-pessoa")
@RequiredArgsConstructor
@Tag(name = "Endereços de Pessoa", description = "Endpoints para gerenciamento de endereços de pessoas")
public class EnderecoPessoaController {

    private final EnderecoPessoaUseCase service;

    @Operation(
            summary = "Listar todos os endereços",
            description = "Retorna todos os endereços cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = EnderecoPessoaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<EnderecoPessoaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllEnderecos());
    }

    @Operation(
            summary = "Buscar endereço por ID",
            description = "Retorna um endereço específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Endereço encontrado",
            content = @Content(schema = @Schema(implementation = EnderecoPessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Endereço não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoPessoaResponseDTO> byId(
            @PathVariable @Validated Integer id) {
        return ResponseEntity.ok(service.getEndereco(id));
    }

    @Operation(
            summary = "Criar endereço",
            description = "Cria um novo endereço para pessoa"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Endereço criado com sucesso",
            content = @Content(schema = @Schema(implementation = EnderecoPessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<EnderecoPessoaResponseDTO> create(
            @RequestBody @Validated EnderecoPessoaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveEndereco(dto));
    }

    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza um endereço existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Endereço atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = EnderecoPessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Endereço não encontrado",
            content = @Content
    )
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoPessoaResponseDTO> update(
            @PathVariable @Validated Integer id,
            @RequestBody EnderecoPessoaRequestDTO dto) {
        return ResponseEntity.ok(service.updateEndereco(id, dto));
    }

    @Operation(
            summary = "Deletar endereço",
            description = "Remove um endereço pelo ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Endereço removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Endereço não encontrado",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Validated Integer id) {
        service.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }
}