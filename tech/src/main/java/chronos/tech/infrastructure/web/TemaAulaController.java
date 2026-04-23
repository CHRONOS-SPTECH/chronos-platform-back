package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TemaAulaRequestDTO;
import chronos.tech.application.dto.response.TemaAulaResponseDTO;
import chronos.tech.application.port.in.TemaAulaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temas-aula")
@RequiredArgsConstructor
public class TemaAulaController {
    private final TemaAulaUseCase service;

    @GetMapping
    public ResponseEntity<List<TemaAulaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllTemas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaAulaResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getTema(id));
    }

    @PostMapping
    public ResponseEntity<TemaAulaResponseDTO> create(@RequestBody @Validated TemaAulaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTema(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemaAulaResponseDTO> update(@PathVariable @Validated Long id, @RequestBody TemaAulaRequestDTO dto) {
        return ResponseEntity.ok(service.updateTema(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteTema(id);
        return ResponseEntity.noContent().build();
    }
}
