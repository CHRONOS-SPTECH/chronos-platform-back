package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.SecretariaRequestDTO;
import chronos.tech.application.dto.response.SecretariaResponseDTO;
import chronos.tech.application.port.in.SecretariaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secretarias")
@RequiredArgsConstructor
public class SecretariaController {

    private final SecretariaUseCase service;

    @GetMapping
    public ResponseEntity<List<SecretariaResponseDTO>> allSecretaria(){
        var resposta = service.getAllSecretaria();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecretariaResponseDTO> getSecretaria(@PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getSecretaria(id));
    }

    @PostMapping
    public ResponseEntity<SecretariaResponseDTO> saveSecretaria(@RequestBody @Validated SecretariaRequestDTO secretaria){
        SecretariaResponseDTO secretariaCriada = service.saveSecretaria(secretaria);
        return ResponseEntity.status(HttpStatus.CREATED).body(secretariaCriada);
    }

    @PutMapping("{id}")
    public ResponseEntity<SecretariaResponseDTO> updateSecretaria(@PathVariable @Validated Long id, @RequestBody SecretariaRequestDTO secretaria){
        SecretariaResponseDTO secretariaAtualizada = service.updateSecretaria(id, secretaria);
        return ResponseEntity.ok().body(secretariaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSecretaria(@PathVariable @Validated Long id){
        service.deletSecretaria(id);
        return ResponseEntity.ok().build();
    }

}
