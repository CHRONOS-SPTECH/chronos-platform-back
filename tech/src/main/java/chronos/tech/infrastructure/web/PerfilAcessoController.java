package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.response.PerfilAcessoResponseDTO;
import chronos.tech.application.port.in.PerfilAcessoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfisAcesso")
@RequiredArgsConstructor
public class PerfilAcessoController {

    private final PerfilAcessoUseCase service;

    @GetMapping
    public ResponseEntity<List<PerfilAcessoResponseDTO>> allPerfilAcesso(){
        var resposta = service.getAllPerfilAcesso();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilAcessoResponseDTO> getPerfilAcesso(@PathVariable @Validated Long id){
        var resposta = service.getPerfilAcesso(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping
    public ResponseEntity<PerfilAcessoResponseDTO> savePerfilAcesso(@RequestBody @Validated PerfilAcessoRequestDTO perfilAcesso){
        PerfilAcessoResponseDTO novoPerfil = service.savePerfilAcesso(perfilAcesso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPerfil);
    }

    @PutMapping("{id}")
    public ResponseEntity<PerfilAcessoResponseDTO> updatePerfilAcesso(@PathVariable @Validated Long id, @RequestBody PerfilAcessoRequestDTO perfilAcesso){
        PerfilAcessoResponseDTO perfilAtualizado = service.updatePerfilAcesso(id, perfilAcesso);
        return ResponseEntity.ok(perfilAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePerfilAcesso(@PathVariable @Validated Long id){
        service.deletPerfilAcesso(id);
        return ResponseEntity.ok().build();
    }

}
