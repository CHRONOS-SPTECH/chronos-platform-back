package chronos.tech.controller;

import chronos.tech.model.classes.TipoParticipacao;
import chronos.tech.service.TipoParticipacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoParticipacoes")
@Tag(name = "Tipo de Participação", description = "API de Tipos de Participação")
public class TipoParticipacaoController {

    @Autowired
    private TipoParticipacaoService service;

    @GetMapping
    @Operation(summary = "Busca todos os tipos de participação")
    public ResponseEntity<List<TipoParticipacao>> allTipoParticipacao(){
        var resposta = service.getAllTipoParticipacao();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um Tipo de participação através do ID")
    public ResponseEntity<Optional<TipoParticipacao>> getTipoParticipacao(@PathVariable @Validated Long id){
        var resposta = service.getTipoParticipacao(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo Tipo de Participação")
    public ResponseEntity<Void> saveTipoParticipacao(@RequestBody @Validated TipoParticipacao tipoParticipacao){
        service.saveTipoParticipacao(tipoParticipacao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza um tipo de participação através do ID")
    public ResponseEntity<String> updateTipoParticipacao(@PathVariable @Validated Long id, @RequestBody TipoParticipacao tipoParticipacao){
        service.updateTipoParticipacao(id, tipoParticipacao);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta um tipo de participação através do ID")
    public ResponseEntity<Void> deleteTipoParticipacao(@PathVariable @Validated Long id){
        service.deletTipoParticipacao(id);
        return ResponseEntity.ok().build();
    }

}
