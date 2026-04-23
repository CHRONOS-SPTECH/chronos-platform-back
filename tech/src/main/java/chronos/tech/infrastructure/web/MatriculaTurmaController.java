package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;
import chronos.tech.application.port.in.MatriculaTurmaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas-turma")
@RequiredArgsConstructor
public class MatriculaTurmaController {
    private final MatriculaTurmaUseCase service;

    @GetMapping
    public ResponseEntity<List<MatriculaTurmaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllMatriculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaTurmaResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getMatricula(id));
    }

    @PostMapping
    public ResponseEntity<MatriculaTurmaResponseDTO> create(@RequestBody @Validated MatriculaTurmaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveMatricula(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaTurmaResponseDTO> update(@PathVariable @Validated Long id, @RequestBody MatriculaTurmaRequestDTO dto) {
        return ResponseEntity.ok(service.updateMatricula(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteMatricula(id);
        return ResponseEntity.noContent().build();
    }
}
