package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.HistoricoAcademicoRequestDTO;
import chronos.tech.application.dto.response.HistoricoAcademicoResponseDTO;
import chronos.tech.application.port.in.HistoricoAcademicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historicos-academicos")
@RequiredArgsConstructor
public class HistoricoAcademicoController {
    private final HistoricoAcademicoUseCase service;

    @GetMapping
    public ResponseEntity<List<HistoricoAcademicoResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllHistoricos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoAcademicoResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getHistorico(id));
    }

    @PostMapping
    public ResponseEntity<HistoricoAcademicoResponseDTO> create(@RequestBody @Validated HistoricoAcademicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveHistorico(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoAcademicoResponseDTO> update(@PathVariable @Validated Long id, @RequestBody HistoricoAcademicoRequestDTO dto) {
        return ResponseEntity.ok(service.updateHistorico(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteHistorico(id);
        return ResponseEntity.noContent().build();
    }
}
