package chronos.tech.controller;

import chronos.tech.dto.response.CategoriaAtividadeResponseDto;
import chronos.tech.model.classes.CategoriaAtividade;
import chronos.tech.service.CategoriaAtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Categoria de Atividade", description = "API de categorias de atividade")
@RequestMapping("/categoriasAtividade")
public class CategoriaAtividadeController {

    @Autowired
    private CategoriaAtividadeService service;

    @GetMapping
    @Operation(summary = "Busca todas as categorias de atividade")
    public ResponseEntity<List<CategoriaAtividade>> allCategoriaAtividade(){
        return ResponseEntity.ok().body(service.getAllCategoriaAtividade());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma determinada categoria de atividade através do ID")
    public ResponseEntity<Optional<CategoriaAtividadeResponseDto>> getCategoriaAtividade(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getCategoriaAtividade(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova categoria de atividade")
    public ResponseEntity<Void> saveCategoriaAtividade(@RequestBody @Validated CategoriaAtividade categoriaAtividade){
        service.saveCategoriaAtividade(categoriaAtividade);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza uma categoria de atividade através do ID")
    public ResponseEntity<String> updateCategoriaAtividade(@PathVariable @Validated Long id, @RequestBody CategoriaAtividade categoriaAtividade){
        service.updateCategoriaAtividade(id, categoriaAtividade);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta uma categoria de atividade através do ID")
    public ResponseEntity<Void> deleteCategoriaAtividade(@PathVariable @Validated Long id){
        service.deletCategoriaAtividade(id);
        return ResponseEntity.ok().build();
    }

}
