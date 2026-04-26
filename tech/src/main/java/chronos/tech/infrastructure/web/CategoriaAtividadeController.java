package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.CategoriaAtividadeRequestDTO;
import chronos.tech.application.dto.response.CategoriaAtividadeResponseDTO;
import chronos.tech.application.port.in.CategoriaAtividadeUseCase;
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
@RequestMapping("/categoriasAtividade")
@RequiredArgsConstructor
@Tag(name = "Categorias de Atividade", description = "Endpoints para gerenciamento de categorias de atividade")
public class CategoriaAtividadeController {

    private final CategoriaAtividadeUseCase service;

    @Operation(
            summary = "Listar todas as categorias",
            description = "Retorna todas as categorias de atividade cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = CategoriaAtividadeResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<CategoriaAtividadeResponseDTO>> allCategoriaAtividade(){
        return ResponseEntity.ok().body(service.getAllCategoriaAtividade());
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna uma categoria específica pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categoria encontrada",
            content = @Content(schema = @Schema(implementation = CategoriaAtividadeResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaAtividadeResponseDTO> getCategoriaAtividade(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getCategoriaAtividade(id));
    }

    @Operation(
            summary = "Criar categoria",
            description = "Cria uma nova categoria de atividade"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso",
            content = @Content(schema = @Schema(implementation = CategoriaAtividadeResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<CategoriaAtividadeResponseDTO> saveCategoriaAtividade(
            @RequestBody @Validated CategoriaAtividadeRequestDTO categoriaAtividade){
        CategoriaAtividadeResponseDTO categoriaCriada = service.saveCategoriaAtividade(categoriaAtividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categoria atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = CategoriaAtividadeResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<CategoriaAtividadeResponseDTO> updateCategoriaAtividade(
            @PathVariable @Validated Long id,
            @RequestBody CategoriaAtividadeRequestDTO categoriaAtividade){
        CategoriaAtividadeResponseDTO categoriaAtualizada = service.updateCategoriaAtividade(id, categoriaAtividade);
        return ResponseEntity.ok().body(categoriaAtualizada);
    }

    @Operation(
            summary = "Deletar categoria",
            description = "Remove uma categoria pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categoria removida com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategoriaAtividade(
            @PathVariable @Validated Long id){
        service.deletCategoriaAtividade(id);
        return ResponseEntity.ok().build();
    }
}