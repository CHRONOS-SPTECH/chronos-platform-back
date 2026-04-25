package chronos.tech.controller;

import chronos.tech.model.classes.PerfilAcesso;
import chronos.tech.service.PerfilAcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/PerfisAcesso")
@Tag(name = "Perfil de acesso", description = "API de perfis de acesso")
public class PerfilAcessoController {

    @Autowired
    private PerfilAcessoService service;

    @GetMapping
    @Operation(summary = "Busca todos os perfis de acesso")
    public ResponseEntity<List<PerfilAcesso>> allPerfilAcesso(){
        var resposta = service.getAllPerfilAcesso();
        return ResponseEntity.ok().body(resposta);
    }

    @Operation(summary = "Busca um perfil de acesso através do ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<PerfilAcesso>> getPerfilAcesso(@PathVariable @Validated Long id){
        var resposta = service.getPerfilAcesso(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo perfil de acesso")
    public ResponseEntity<Void> savePerfilAcesso(@RequestBody @Validated PerfilAcesso perfilAcesso){
        service.savePerfilAcesso(perfilAcesso);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza um perfil de acesso através do ID")
    public ResponseEntity<String> updatePerfilAcesso(@PathVariable @Validated Long id, @RequestBody PerfilAcesso perfilAcesso){
        service.updatePerfilAcesso(id, perfilAcesso);
        return ResponseEntity.ok().body("Deu certo");

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta um perfil de acesso através do ID")
    public ResponseEntity<Void> deletePerfilAcesso(@PathVariable @Validated Long id){
        service.deletPerfilAcesso(id);
        return ResponseEntity.ok().build();
    }

}
