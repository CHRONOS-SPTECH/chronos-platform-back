package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.MateriaRequestDTO;
import chronos.tech.application.dto.response.MateriaResponseDTO;
import chronos.tech.application.port.in.MateriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
@RequiredArgsConstructor
public class MateriaController {
    private final MateriaUseCase service;

    @GetMapping
    public ResponseEntity<List<MateriaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllMaterias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getMateria(id));
    }

    @PostMapping
    public ResponseEntity<MateriaResponseDTO> create(@RequestBody @Validated MateriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveMateria(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> update(@PathVariable @Validated Long id, @RequestBody MateriaRequestDTO dto) {
        return ResponseEntity.ok(service.updateMateria(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteMateria(id);
        return ResponseEntity.noContent().build();
    }
}
