package chronos.tech.controller;

import chronos.tech.model.classes.Pessoa;
import chronos.tech.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity <List<Pessoa>> getAllPersons() {

        return ResponseEntity.ok().body(service.getAllPersons());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pessoa>> getPessoa(@PathVariable @Validated Long id){
        var resposta = service.getPessoa(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> savePessoa(@RequestBody @Validated Pessoa pessoa){
        service.savePessoa(pessoa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatePessoa(@PathVariable @Validated Long id, @RequestBody Pessoa pessoa){
        service.updatePessoa(id, pessoa);
        return ResponseEntity.ok().body("Deu certo");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable @Validated Long id){
        service.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}
