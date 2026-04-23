package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.UsuarioRequestDTO;
import chronos.tech.application.dto.response.UsuarioResponseDTO;
import chronos.tech.application.port.in.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Validated UsuarioRequestDTO usuario){
        UsuarioResponseDTO novoUsuario = service.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.pegarTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable @Validated Long id) {
        return ResponseEntity.ok(service.pegarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable @Validated Long id,
                                                        @RequestBody @Validated UsuarioRequestDTO usuario) {
        return ResponseEntity.ok(service.atualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Validated Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
