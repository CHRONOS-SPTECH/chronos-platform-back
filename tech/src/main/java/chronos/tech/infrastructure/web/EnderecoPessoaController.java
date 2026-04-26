package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.EnderecoPessoaRequestDTO;
import chronos.tech.application.dto.response.EnderecoPessoaResponseDTO;
import chronos.tech.application.port.in.EnderecoPessoaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos-pessoa")
@RequiredArgsConstructor
public class EnderecoPessoaController {

    private final EnderecoPessoaUseCase service;

    @GetMapping
    public ResponseEntity<List<EnderecoPessoaResponseDTO>> all() {
        return ResponseEntity.ok(service.getAllEnderecos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoPessoaResponseDTO> byId(@PathVariable @Validated Integer id) {
        return ResponseEntity.ok(service.getEndereco(id));
    }
    @PostMapping
    public ResponseEntity<EnderecoPessoaResponseDTO> create(@RequestBody @Validated EnderecoPessoaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveEndereco(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoPessoaResponseDTO> update(@PathVariable @Validated Integer id, @RequestBody EnderecoPessoaRequestDTO dto) {
        return ResponseEntity.ok(service.updateEndereco(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Validated Integer id) {
        service.deleteEndereco(id); return ResponseEntity.noContent().build();
    }
}
