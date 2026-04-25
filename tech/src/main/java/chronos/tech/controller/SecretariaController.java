package chronos.tech.controller;

import chronos.tech.model.classes.Secretaria;
import chronos.tech.service.SecretariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(name = "/secretarias")
@Tag(name = "Secretaria", description = "API de Secretaria")
public class SecretariaController {

    @Autowired
    private SecretariaService service;

    @GetMapping
    @Operation(summary = "Buscar todas as secretarias")
    public ResponseEntity<List<Secretaria>> allSecretaria(){
        var resposta = service.getAllSecretaria();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma secretaria através do ID")
    public ResponseEntity<Optional<Secretaria>> getSecretaria(@PathVariable @Validated Long id){
        var resposta = service.getSecretaria(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova secretaria")
    public ResponseEntity<Void> saveSecretaria(@RequestBody @Validated Secretaria secretaria){
        service.saveSecretaria(secretaria);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma secretaria através do ID")
    public ResponseEntity<String> updateSecretaria(@PathVariable @Validated Long id, @RequestBody Secretaria secretaria){
        service.updateSecretaria(id, secretaria);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar uma secretaria através do ID")
    public ResponseEntity<Void> deleteSecretaria(@PathVariable @Validated Long id){
        service.deletSecretaria(id);
        return ResponseEntity.ok().build();
    }

}
