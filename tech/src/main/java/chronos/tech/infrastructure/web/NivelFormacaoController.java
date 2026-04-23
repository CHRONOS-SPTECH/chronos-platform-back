package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.NivelFormacaoRequestDTO;
import chronos.tech.application.dto.response.NivelFormacaoResponseDTO;
import chronos.tech.application.port.in.NivelFormacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/niveisFormacao")
@RequiredArgsConstructor
public class NivelFormacaoController {

    private final NivelFormacaoUseCase service;

    @GetMapping
    public ResponseEntity<List<NivelFormacaoResponseDTO>> allNivelFormacao(){
        var resposta = service.getAllNivelFormacao();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelFormacaoResponseDTO> getNivelFormacao(@PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getNivelFormacao(id));
    }

    @PostMapping
    public ResponseEntity<NivelFormacaoResponseDTO> saveNivelFormacao(@RequestBody @Validated NivelFormacaoRequestDTO nivelFormacao){
        NivelFormacaoResponseDTO nivelCriado = service.saveNivelFormacao(nivelFormacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(nivelCriado);
    }

    @PutMapping("{id}")
    public ResponseEntity<NivelFormacaoResponseDTO> updateNivelFormacao(@PathVariable @Validated Long id, @RequestBody NivelFormacaoRequestDTO nivelFormacao){
        NivelFormacaoResponseDTO nivelAtualizado = service.updateNivelFormacao(id, nivelFormacao);
        return ResponseEntity.ok().body(nivelAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNivelFormacao(@PathVariable @Validated Long id){
        service.deletNivelFormacao(id);
        return ResponseEntity.ok().build();
    }

}
