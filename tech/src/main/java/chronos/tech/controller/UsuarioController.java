package chronos.tech.controller;

import chronos.tech.dto.CreateUsuarioRequestDTO;
import chronos.tech.dto.UsuarioResponseDTO;
import chronos.tech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Validated CreateUsuarioRequestDTO usuario){
        UsuarioResponseDTO novoUsuario = service.criarUsuario(usuario);
        return ResponseEntity.status(201).body(novoUsuario);
    }

//    @GetMapping
//    public ResponseEntity<UsuarioResponseDTO> login(@Path)

}
