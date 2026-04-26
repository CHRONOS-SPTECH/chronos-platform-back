package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.application.port.in.ChamadaAulaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamadas-aula")
@RequiredArgsConstructor
public class ChamadaAulaController {

    private final ChamadaAulaUseCase service;

    @GetMapping
    public ResponseEntity<List<ChamadaAulaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllChamadas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChamadaAulaResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getChamada(id));
    }
    @PostMapping
    public ResponseEntity<ChamadaAulaResponseDTO> create(@RequestBody @Validated ChamadaAulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveChamada(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ChamadaAulaResponseDTO> update(@PathVariable @Validated Long id, @RequestBody ChamadaAulaRequestDTO dto) {
        return ResponseEntity.ok(service.updateChamada(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteChamada(id); return ResponseEntity.noContent().build();
    }
}
