package chronos.tech.infrastructure.adapter.input.controller;

import chronos.tech.application.dto.PessoaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (
        "/Pessoa"
)
public class PessoaController {

    @GetMapping("/{id}")
    public ResponseEntity <PessoaResponseDto> getPessoa(@PathVariable Long id) {

        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
