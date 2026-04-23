package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.CategoriaAtividadeRequestDTO;
import chronos.tech.application.dto.response.CategoriaAtividadeResponseDTO;
import chronos.tech.application.port.in.CategoriaAtividadeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoriasAtividade")
@RequiredArgsConstructor
public class CategoriaAtividadeController {

    private final CategoriaAtividadeUseCase service;

    @GetMapping
    public ResponseEntity<List<CategoriaAtividadeResponseDTO>> allCategoriaAtividade(){
        return ResponseEntity.ok().body(service.getAllCategoriaAtividade());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaAtividadeResponseDTO> getCategoriaAtividade(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.getCategoriaAtividade(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaAtividadeResponseDTO> saveCategoriaAtividade(@RequestBody @Validated CategoriaAtividadeRequestDTO categoriaAtividade){
        CategoriaAtividadeResponseDTO categoriaCriada = service.saveCategoriaAtividade(categoriaAtividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoriaAtividadeResponseDTO> updateCategoriaAtividade(@PathVariable @Validated Long id, @RequestBody CategoriaAtividadeRequestDTO categoriaAtividade){
        CategoriaAtividadeResponseDTO categoriaAtualizada = service.updateCategoriaAtividade(id, categoriaAtividade);
        return ResponseEntity.ok().body(categoriaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategoriaAtividade(@PathVariable @Validated Long id){
        service.deletCategoriaAtividade(id);
        return ResponseEntity.ok().build();
    }

}
