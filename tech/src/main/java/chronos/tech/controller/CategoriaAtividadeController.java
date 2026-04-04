package chronos.tech.controller;

import chronos.tech.model.classes.CategoriaAtividade;
import chronos.tech.service.CategoriaAtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoriasAtividade")
public class CategoriaAtividadeController {

    @Autowired
    private CategoriaAtividadeService service;

    @GetMapping
    public ResponseEntity<List<CategoriaAtividade>> allCategoriaAtividade(){
        var resposta = service.getAllCategoriaAtividade();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoriaAtividade>> getCategoriaAtividade(@PathVariable @Validated Long id){
        var resposta = service.getCategoriaAtividade(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> saveCategoriaAtividade(@RequestBody @Validated CategoriaAtividade categoriaAtividade){
        service.saveCategoriaAtividade(categoriaAtividade);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCategoriaAtividade(@PathVariable @Validated Long id, @RequestBody CategoriaAtividade categoriaAtividade){
        service.updateCategoriaAtividade(id, categoriaAtividade);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategoriaAtividade(@PathVariable @Validated Long id){
        service.deletCategoriaAtividade(id);
        return ResponseEntity.ok().build();
    }

}
