package chronos.tech.controller;

import chronos.tech.model.classes.NivelFormacao;
import chronos.tech.service.NivelFormacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/niveisFormacao")
public class NivelFormacaoController {

    @Autowired
    private NivelFormacaoService service;

    @GetMapping
    public ResponseEntity<List<NivelFormacao>> allNivelFormacao(){
        var resposta = service.getAllNivelFormacao();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<NivelFormacao>> getNivelFormacao(@PathVariable @Validated Long id){
        var resposta = service.getNivelFormacao(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> saveNivelFormacao(@RequestBody @Validated NivelFormacao nivelFormacao){
        service.saveNivelFormacao(nivelFormacao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateNivelFormacao(@PathVariable @Validated Long id, @RequestBody NivelFormacao nivelFormacao){
        service.updateNivelFormacao(id, nivelFormacao);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNivelFormacao(@PathVariable @Validated Long id){
        service.deletNivelFormacao(id);
        return ResponseEntity.ok().build();
    }

}
