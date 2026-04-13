package chronos.tech.controller;

import chronos.tech.model.classes.PerfilAcesso;
import chronos.tech.service.PerfilAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfisAcesso")
public class PerfilAcessoController {

    @Autowired
    private PerfilAcessoService service;

    @GetMapping
    public ResponseEntity<List<PerfilAcesso>> allPerfilAcesso(){
        var resposta = service.getAllPerfilAcesso();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PerfilAcesso>> getPerfilAcesso(@PathVariable @Validated Long id){
        var resposta = service.getPerfilAcesso(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> savePerfilAcesso(@RequestBody @Validated PerfilAcesso perfilAcesso){
        service.savePerfilAcesso(perfilAcesso);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatePerfilAcesso(@PathVariable @Validated Long id, @RequestBody PerfilAcesso perfilAcesso){
        service.updatePerfilAcesso(id, perfilAcesso);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePerfilAcesso(@PathVariable @Validated Long id){
        service.deletPerfilAcesso(id);
        return ResponseEntity.ok().build();
    }

}
