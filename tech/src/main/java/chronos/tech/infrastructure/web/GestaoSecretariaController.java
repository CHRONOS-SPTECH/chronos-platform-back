package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.GestaoSecretariaRequestDTO;
import chronos.tech.application.dto.response.GestaoSecretariaResponseDTO;
import chronos.tech.application.port.in.GestaoSecretariaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestoes-secretaria")
@RequiredArgsConstructor
public class GestaoSecretariaController {
    private final GestaoSecretariaUseCase service;

    @GetMapping
    public ResponseEntity<List<GestaoSecretariaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllGestoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestaoSecretariaResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getGestao(id));
    }

    @PostMapping
    public ResponseEntity<GestaoSecretariaResponseDTO> create(@RequestBody @Validated GestaoSecretariaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveGestao(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GestaoSecretariaResponseDTO> update(@PathVariable @Validated Long id, @RequestBody GestaoSecretariaRequestDTO dto) {
        return ResponseEntity.ok(service.updateGestao(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteGestao(id);
        return ResponseEntity.noContent().build();
    }
}
