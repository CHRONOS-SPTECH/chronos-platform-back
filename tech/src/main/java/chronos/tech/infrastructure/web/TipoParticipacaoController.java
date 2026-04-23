package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.TipoParticipacaoRequestDTO;
import chronos.tech.application.dto.response.TipoParticipacaoResponseDTO;
import chronos.tech.application.port.in.TipoParticipacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoParticipacoes")
@RequiredArgsConstructor
public class TipoParticipacaoController {

    private final TipoParticipacaoUseCase service;

    @GetMapping
    public ResponseEntity<List<TipoParticipacaoResponseDTO>> allTipoParticipacao(){
        var resposta = service.getAllTipoParticipacao();
        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoParticipacaoResponseDTO> getTipoParticipacao(@PathVariable @Validated Long id){
        return ResponseEntity.ok().body(service.getTipoParticipacao(id));
    }

    @PostMapping
    public ResponseEntity<TipoParticipacaoResponseDTO> saveTipoParticipacao(@RequestBody @Validated TipoParticipacaoRequestDTO tipoParticipacao){
        TipoParticipacaoResponseDTO tipoCriado = service.saveTipoParticipacao(tipoParticipacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoCriado);
    }

    @PutMapping("{id}")
    public ResponseEntity<TipoParticipacaoResponseDTO> updateTipoParticipacao(@PathVariable @Validated Long id, @RequestBody TipoParticipacaoRequestDTO tipoParticipacao){
        TipoParticipacaoResponseDTO tipoAtualizado = service.updateTipoParticipacao(id, tipoParticipacao);
        return ResponseEntity.ok().body(tipoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTipoParticipacao(@PathVariable @Validated Long id){
        service.deleteTipoParticipacao(id);
        return ResponseEntity.ok().build();
    }

}
