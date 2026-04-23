package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.application.port.in.TurmaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaUseCase service;

    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> getAllTurmas() {
        return ResponseEntity.ok().body(service.getAllTurmas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> getTurma(@PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getTurma(id));
    }

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> saveTurma(@RequestBody @Validated TurmaRequestDTO turma){
        TurmaResponseDTO turmaCriada = service.saveTurma(turma);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaCriada);
    }

    @PutMapping("{id}")
    public ResponseEntity<TurmaResponseDTO> updateTurma(@PathVariable @Validated Long id, @RequestBody TurmaRequestDTO turma){
        TurmaResponseDTO turmaAtualizada = service.updateTurma(id, turma);
        return ResponseEntity.ok().body(turmaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable @Validated Long id){
        service.deleteTurma(id);
        return ResponseEntity.ok().build();
    }

}
