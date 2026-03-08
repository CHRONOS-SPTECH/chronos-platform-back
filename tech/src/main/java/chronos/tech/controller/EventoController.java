package chronos.tech.controller;

import chronos.tech.model.classes.Evento;
import chronos.tech.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    public ResponseEntity<List<Evento>> allEventos(){
        var resposta = service.getAllEventos();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Evento>> getEvento(@PathVariable @Validated Long id){
        var resposta = service.getEvento(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> saveEvento(@RequestBody @Validated Evento evento){
        service.saveEvento(evento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateEvento(@PathVariable @Validated Long id, @RequestBody Evento evento){
        service.updateEvento(id, evento);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable @Validated Long id){
        service.deletEvento(id);
        return ResponseEntity.ok().build();
    }
}
