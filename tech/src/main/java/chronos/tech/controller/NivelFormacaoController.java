package chronos.tech.controller;

import chronos.tech.model.classes.NivelFormacao;
import chronos.tech.service.NivelFormacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(name = "/niveisFormacao")
@Tag(name = "Nivel de Formação", description = "API de níveis de formação")
public class NivelFormacaoController {

    @Autowired
    private NivelFormacaoService service;

    @GetMapping
    @Operation(summary = "Buscar todos os níveis de formação")
    public ResponseEntity<List<NivelFormacao>> allNivelFormacao(){
        var resposta = service.getAllNivelFormacao();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um nível de formação através do ID")
    public ResponseEntity<Optional<NivelFormacao>> getNivelFormacao(@PathVariable @Validated Long id){
        var resposta = service.getNivelFormacao(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    @Operation(summary = "Cadastra um nível de formação")
    public ResponseEntity<Void> saveNivelFormacao(@RequestBody @Validated NivelFormacao nivelFormacao){
        service.saveNivelFormacao(nivelFormacao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza um nível de formação através do ID")
    public ResponseEntity<String> updateNivelFormacao(@PathVariable @Validated Long id, @RequestBody NivelFormacao nivelFormacao){
        service.updateNivelFormacao(id, nivelFormacao);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta um nível de formação através do ID")
    public ResponseEntity<Void> deleteNivelFormacao(@PathVariable @Validated Long id){
        service.deletNivelFormacao(id);
        return ResponseEntity.ok().build();
    }

}
