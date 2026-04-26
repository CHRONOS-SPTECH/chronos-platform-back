package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.port.in.AulaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaUseCase service;

    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllAulas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> byId(@PathVariable @Validated Integer id) {
        return ResponseEntity.ok(service.getAula(id));
    }
    @PostMapping
    public ResponseEntity<AulaResponseDTO> create(@RequestBody @Validated AulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAula(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> update(@PathVariable @Validated Integer id, @RequestBody AulaRequestDTO dto) {
        return ResponseEntity.ok(service.updateAula(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Integer id) {
        service.deleteAula(id); return ResponseEntity.noContent().build();
    }
}
