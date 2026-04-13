package chronos.tech.controller;

import chronos.tech.model.classes.Secretaria;
import chronos.tech.service.SecretariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/secretarias")
public class SecretariaController {

    @Autowired
    private SecretariaService service;

    @GetMapping
    public ResponseEntity<List<Secretaria>> allSecretaria(){
        var resposta = service.getAllSecretaria();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Secretaria>> getSecretaria(@PathVariable @Validated Long id){
        var resposta = service.getSecretaria(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> saveSecretaria(@RequestBody @Validated Secretaria secretaria){
        service.saveSecretaria(secretaria);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateSecretaria(@PathVariable @Validated Long id, @RequestBody Secretaria secretaria){
        service.updateSecretaria(id, secretaria);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSecretaria(@PathVariable @Validated Long id){
        service.deletSecretaria(id);
        return ResponseEntity.ok().build();
    }

}
