package chronos.tech.controller;

import chronos.tech.dto.CreatePessoaRequestDto;
import chronos.tech.dto.PessoaResponseDto;
import chronos.tech.model.classes.Pessoa;
import chronos.tech.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> getPessoa(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.pegarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDto> savePessoa(@RequestBody @Validated CreatePessoaRequestDto pessoaRequestDto){
        PessoaResponseDto pessoaResponseDto = service.createPessoa(pessoaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDto);
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
