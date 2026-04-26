package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TipoVinculoRequestDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;
import chronos.tech.application.port.in.TipoVinculoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-vinculo")
@RequiredArgsConstructor
public class TipoVinculoController {

    private final TipoVinculoUseCase service;

    @GetMapping
    public ResponseEntity<List<TipoVinculoResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllTiposVinculo());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoVinculoResponseDTO> byId(@PathVariable @Validated Integer id) {
        return ResponseEntity.ok(service.getTipoVinculo(id));
    }
    @PostMapping
    public ResponseEntity<TipoVinculoResponseDTO> create(@RequestBody @Validated TipoVinculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTipoVinculo(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TipoVinculoResponseDTO> update(@PathVariable @Validated Integer id, @RequestBody TipoVinculoRequestDTO dto) {
        return ResponseEntity.ok(service.updateTipoVinculo(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Integer id) {
        service.deleteTipoVinculo(id); return ResponseEntity.noContent().build();
    }
}
