package chronos.tech.controller;

import chronos.tech.model.classes.TipoParticipacao;
import chronos.tech.service.TipoParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoParticipacoes")
public class TipoParticipacaoController {

    @Autowired
    private TipoParticipacaoService service;

    @GetMapping
    public ResponseEntity<List<TipoParticipacao>> allTipoParticipacao(){
        var resposta = service.getAllTipoParticipacao();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TipoParticipacao>> getTipoParticipacao(@PathVariable @Validated Long id){
        var resposta = service.getTipoParticipacao(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> saveTipoParticipacao(@RequestBody @Validated TipoParticipacao tipoParticipacao){
        service.saveTipoParticipacao(tipoParticipacao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateTipoParticipacao(@PathVariable @Validated Long id, @RequestBody TipoParticipacao tipoParticipacao){
        service.updateTipoParticipacao(id, tipoParticipacao);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTipoParticipacao(@PathVariable @Validated Long id){
        service.deletTipoParticipacao(id);
        return ResponseEntity.ok().build();
    }

}
