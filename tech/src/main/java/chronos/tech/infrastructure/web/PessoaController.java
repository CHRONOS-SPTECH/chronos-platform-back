package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.port.in.PessoaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaUseCase service;

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> getAllPersons() {
        return ResponseEntity.ok().body(service.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> getPessoa(@PathVariable @Validated Long id){
        return ResponseEntity.ok(service.pegarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> savePessoa(@RequestBody @Validated PessoaRequestDTO pessoaRequestDto){
        PessoaResponseDTO pessoaResponseDto = service.createPessoa(pessoaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable @Validated Long id, @RequestBody PessoaRequestDTO pessoa){
        PessoaResponseDTO pessoaAtualizada = service.updatePessoa(id, pessoa);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable @Validated Long id){
        service.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}
