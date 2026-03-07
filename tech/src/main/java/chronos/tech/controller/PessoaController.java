package chronos.tech.controller;

import chronos.tech.model.classes.Pessoa;
import chronos.tech.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity <List<Pessoa>> getAllPersons() {

        return ResponseEntity.ok().body(service.getAllPersons());

    }

}
