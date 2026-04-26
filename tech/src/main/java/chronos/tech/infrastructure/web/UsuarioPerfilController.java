package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.UsuarioPerfilRequestDTO;
import chronos.tech.application.dto.response.UsuarioPerfilResponseDTO;
import chronos.tech.application.port.in.UsuarioPerfilUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios-perfis")
@RequiredArgsConstructor
public class UsuarioPerfilController {

    private final UsuarioPerfilUseCase service;

    @GetMapping
    public ResponseEntity<List<UsuarioPerfilResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllVinculos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPerfilResponseDTO> byId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.getVinculo(id));
    }
    @PostMapping
    public ResponseEntity<UsuarioPerfilResponseDTO> create(@RequestBody @Validated UsuarioPerfilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveVinculo(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioPerfilResponseDTO> update(@PathVariable @Validated Long id, @RequestBody UsuarioPerfilRequestDTO dto) {
        return ResponseEntity.ok(service.updateVinculo(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Long id) {
        service.deleteVinculo(id); return ResponseEntity.noContent().build();
    }
}
