package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.pessoa.PessoaRegistroRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaBiometriaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaFichaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaPublicoExternoListaResponseDTO;
import chronos.tech.application.port.in.PessoaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
@Tag(name = "Pessoas", description = "Endpoints para gerenciamento de pessoas")
@SecurityRequirement(name = "bearerAuth")
public class PessoaController {

    private final PessoaUseCase service;

    @Operation(
            summary = "Listar todas as pessoas",
            description = "Retorna pessoas ativas com vínculo Público Externo, paginadas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PessoaPublicoExternoListaResponseDTO.class))
    )
    @GetMapping
    public ResponseEntity<Page<PessoaPublicoExternoListaResponseDTO>> getAllPersons(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(service.getAllPersons(pageable));
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
            summary = "Listar todas as pessoas detalhadas",
            description = "Retorna todas as pessoas com os dados do vínculo"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = PessoaDetalhadaResponseDTO.class)
            )
    )
    @GetMapping("/details")
    public ResponseEntity<Page<PessoaDetalhadaResponseDTO>> getAllPersonsDetails(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(service.getAllPersonsDetails(pageable));
    }

    @Operation(
            summary = "Buscar ficha detalhada da pessoa por ID",
            description = "Retorna dados pessoais, endereço, vínculo, turmas, presença, histórico e indicador de biometria"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pessoa encontrada",
            content = @Content(schema = @Schema(implementation = PessoaFichaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pessoa não encontrada",
            content = @Content
    )
    @GetMapping("/details/{id}")
    public ResponseEntity<PessoaFichaResponseDTO> getPersonDetailsId(
            @PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getPersonsDetailsId(id));
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
            @RequestBody @Valid PessoaRequestDTO pessoaRequestDto){
        PessoaResponseDTO pessoaResponseDto = service.createPessoa(pessoaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDto);
    }

    @Operation(
            summary = "Registrar aluno com biometria e imagem de perfil via JSON",
            description = "Cria um novo aluno enviando imagem em Base64 e biometria facial em JSON puro"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Aluno registrado com sucesso",
            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content
    )
    @PostMapping("/registro")
    public ResponseEntity<PessoaResponseDTO> registrarPessoaComBiometria(
            @RequestBody @Valid PessoaRegistroRequestDTO pessoaRegistroRequestDTO){
        PessoaResponseDTO response = service.createPessoaComBiometria(pessoaRegistroRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(
            @PathVariable @Validated Long id,
            @RequestBody @Valid PessoaRequestDTO pessoa){
        PessoaResponseDTO pessoaAtualizada = service.updatePessoa(id, pessoa);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @Operation(summary = "Inativar pessoa", description = "Preenche a data de saída e preserva o histórico da pessoa")
    @ApiResponse(
            responseCode = "204",
            description = "Pessoa inativada com sucesso",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pessoa não encontrada",
            content = @Content
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(
            @PathVariable @Validated Long id){
        service.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativacao")
    public ResponseEntity<Void> inativarPessoa(@PathVariable Long id) {
        service.inativarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativacao")
    public ResponseEntity<Void> reativarPessoa(@PathVariable Long id) {
        service.reativarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/biometria")
    public ResponseEntity<PessoaResponseDTO> atualizarBiometria(
            @PathVariable Long id,
            @RequestBody @Valid PessoaBiometriaRequestDTO request) {
        return ResponseEntity.ok(service.atualizarBiometria(id, request));
    }

    @DeleteMapping("/{id}/biometria")
    public ResponseEntity<Void> removerBiometria(@PathVariable Long id) {
        service.removerBiometria(id);
        return ResponseEntity.noContent().build();
    }

}
