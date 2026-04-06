package chronos.tech.controller;

import chronos.tech.dto.response.CategoriaAtividadeResponseDto;
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
        return ResponseEntity.ok().body(service.getAllCategoriaAtividade());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoriaAtividadeResponseDto>> getCategoriaAtividade(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getCategoriaAtividade(id));
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
