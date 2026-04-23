package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.ParticipacaoRequestDTO;
import chronos.tech.application.dto.response.ParticipacaoResponseDTO;
import chronos.tech.application.port.in.ParticipacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participacoes")
@RequiredArgsConstructor
public class ParticipacaoController {
    private final ParticipacaoUseCase service;

    @GetMapping
    public ResponseEntity<List<ParticipacaoResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllParticipacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipacaoResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getParticipacao(id));
    }

    @PostMapping
    public ResponseEntity<ParticipacaoResponseDTO> create(@RequestBody @Validated ParticipacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveParticipacao(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipacaoResponseDTO> update(@PathVariable @Validated Long id, @RequestBody ParticipacaoRequestDTO dto) {
        return ResponseEntity.ok(service.updateParticipacao(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteParticipacao(id);
        return ResponseEntity.noContent().build();
    }
}
