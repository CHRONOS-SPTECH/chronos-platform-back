package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.dashboard.DashboardEngajamentoResponseDTO;
import chronos.tech.application.port.in.DashboardEngajamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Endpoints para dados do painel principal")
@SecurityRequirement(name = "bearerAuth")
public class DashboardEngajamentoController {

    private final DashboardEngajamentoUseCase service;

    @Operation(
            summary = "Indicadores de engajamento das atividades",
            description = "Retorna os indicadores da Dashboard de Engajamento de Atividades, "
                    + "calculados a partir dos eventos realizados até hoje e das participações registradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Indicadores calculados com sucesso",
            content = @Content(schema = @Schema(implementation = DashboardEngajamentoResponseDTO.class))
    )
    @GetMapping("/engajamento-atividades")
    public ResponseEntity<DashboardEngajamentoResponseDTO> getEngajamentoAtividades() {
        return ResponseEntity.ok(service.getEngajamentoAtividades());
    }
}
