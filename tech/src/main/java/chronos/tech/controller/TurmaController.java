package chronos.tech.controller;

import chronos.tech.model.classes.Turma;
import chronos.tech.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turma", description = "API de turma")
public class TurmaController {

    @Autowired

    private TurmaService service;

    @Operation(summary = "Buscar todas as turmas")
    @GetMapping
    public ResponseEntity<List<Turma>> getAllTurmas() {

        return ResponseEntity.ok().body(service.getAllTurmas());

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma turma através do ID")
    public ResponseEntity<Optional<Turma>> getTurma(@PathVariable @Validated Long id){
        var resposta = service.getTurma(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova turma")
    public ResponseEntity<Void> saveTurma(@RequestBody @Validated Turma turma){
        service.saveTurma(turma);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "")
    public ResponseEntity<String> updateTurma(@PathVariable @Validated Long id, @RequestBody Turma turma){
        service.updateTurma(id, turma);
        return ResponseEntity.ok().body("Deu certo");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable @Validated Long id){
        service.deleteTurma(id);
        return ResponseEntity.ok().build();
    }

}
