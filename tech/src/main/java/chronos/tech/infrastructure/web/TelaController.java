package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.cadastro.TelaResponseDTO;
import chronos.tech.domain.model.classes.Tela;
import chronos.tech.domain.port.TelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/telas")
@RequiredArgsConstructor
public class TelaController {
    private final TelaRepository repository;

    @GetMapping
    public ResponseEntity<List<TelaResponseDTO>> getTelas() {
        List<TelaResponseDTO> telas = repository.findAll().stream()
                .filter(tela -> Boolean.TRUE.equals(tela.getStatusAtivo()))
                .sorted(Comparator.comparing(Tela::getOrdem, Comparator.nullsLast(Integer::compareTo)))
                .map(tela -> new TelaResponseDTO(
                        tela.getIdTela(), tela.getCodigoTela(), tela.getNomeTela(),
                        tela.getGrupo(), tela.getOrdem(), tela.getStatusAtivo()))
                .toList();
        return ResponseEntity.ok(telas);
    }
}

