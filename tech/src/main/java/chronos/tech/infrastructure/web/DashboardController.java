package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.response.dashboard.*;
import chronos.tech.application.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Endpoints para dados do painel principal")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoResponseDTO> getResumo() {
        return ResponseEntity.ok(service.getResumo());
    }

    @GetMapping("/instrutores")
    public ResponseEntity<DashboardInstrutoresResponseDTO> getInstrutores() {
        return ResponseEntity.ok(service.getInstrutores());
    }

    @GetMapping("/alunos-por-nivel")
    public ResponseEntity<List<DashboardNivelResponseDTO>> getAlunosPorNivel() {
        return ResponseEntity.ok(service.getAlunosPorNivel());
    }

    @GetMapping("/genero")
    public ResponseEntity<DashboardGeneroResponseDTO> getGenero() {
        return ResponseEntity.ok(service.getGenero());
    }

    @GetMapping("/faixa-etaria")
    public ResponseEntity<DashboardFaixaEtariaResponseDTO> getFaixaEtaria() {
        return ResponseEntity.ok(service.getFaixaEtaria());
    }
}