package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.port.in.PessoaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Consulta de alunos")
@SecurityRequirement(name = "bearerAuth")
public class AlunoController {

    private final PessoaUseCase pessoaService;

    @Operation(summary = "Listar todos os alunos", description = "Retorna alunos de todas as turmas com paginação")
    @ApiResponse(responseCode = "200", description = "Página de alunos retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<Page<PessoaResponseDTO>> getAllAlunos(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(pessoaService.getAllAlunos(pageable));
    }
}
