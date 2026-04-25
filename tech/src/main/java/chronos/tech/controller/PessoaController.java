package chronos.tech.controller;

import chronos.tech.dto.request.CreatePessoaRequestDto;
import chronos.tech.dto.response.PessoaResponseDto;
import chronos.tech.model.classes.Pessoa;
import chronos.tech.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa", description = "API de Pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    @Operation(summary = "Buscar todas as pessoas")
    public ResponseEntity <List<Pessoa>> getAllPersons() {

        return ResponseEntity.ok().body(service.getAllPersons());

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma pessoa através do ID")
    public ResponseEntity<PessoaResponseDto> getPessoa(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.pegarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma pessoa")
    public ResponseEntity<PessoaResponseDto> savePessoa(@RequestBody @Validated CreatePessoaRequestDto pessoaRequestDto){
        PessoaResponseDto pessoaResponseDto = service.createPessoa(pessoaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDto);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma pessoa através do ID")
    public ResponseEntity<String> updatePessoa(@PathVariable @Validated Long id, @RequestBody Pessoa pessoa){
        service.updatePessoa(id, pessoa);
        return ResponseEntity.ok().body("Deu certo");
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar uma Pessoa através do ID")
    public ResponseEntity<Void> deletePessoa(@PathVariable @Validated Long id){
        service.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}
