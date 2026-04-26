package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TipoParticipacaoRequestDTO;
import chronos.tech.application.dto.response.TipoParticipacaoResponseDTO;
import chronos.tech.application.port.in.TipoParticipacaoUseCase;
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
@RequestMapping("/tipo-participacoes")
@RequiredArgsConstructor
@Tag(name = "Tipos de Participação", description = "Endpoints para gerenciamento de tipos de participação")
public class TipoParticipacaoController {

    private final TipoParticipacaoUseCase service;

    @Operation(
            summary = "Listar todos os tipos de participação",
            description = "Retorna todos os tipos de participação cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = TipoParticipacaoResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<TipoParticipacaoResponseDTO>> allTipoParticipacao(){
        var resposta = service.getAllTipoParticipacao();
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(
            summary = "Buscar tipo de participação por ID",
            description = "Retorna um tipo de participação específico pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tipo encontrado",
            content = @Content(schema = @Schema(implementation = TipoParticipacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tipo não encontrado",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<TipoParticipacaoResponseDTO> getTipoParticipacao(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getTipoParticipacao(id));
    }

    @Operation(
            summary = "Criar tipo de participação",
            description = "Cria um novo tipo de participação"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Tipo criado com sucesso",
            content = @Content(schema = @Schema(implementation = TipoParticipacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping
    public ResponseEntity<TipoParticipacaoResponseDTO> saveTipoParticipacao(
            @RequestBody @Validated TipoParticipacaoRequestDTO tipoParticipacao){
        TipoParticipacaoResponseDTO tipoCriado = service.saveTipoParticipacao(tipoParticipacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoCriado);
    }

    @Operation(
            summary = "Atualizar tipo de participação",
            description = "Atualiza um tipo de participação existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tipo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = TipoParticipacaoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tipo não encontrado",
            content = @Content
    )
    @PutMapping("{id}")
    public ResponseEntity<TipoParticipacaoResponseDTO> updateTipoParticipacao(
            @PathVariable @Validated Long id,
            @RequestBody TipoParticipacaoRequestDTO tipoParticipacao){
        TipoParticipacaoResponseDTO tipoAtualizado = service.updateTipoParticipacao(id, tipoParticipacao);
        return ResponseEntity.ok().body(tipoAtualizado);
    }

    @Operation(
            summary = "Deletar tipo de participação",
            description = "Remove um tipo de participação pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tipo removido com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tipo não encontrado",
            content = @Content
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTipoParticipacao(
            @PathVariable @Validated Long id){
        service.deleteTipoParticipacao(id);
        return ResponseEntity.ok().build();
    }

}