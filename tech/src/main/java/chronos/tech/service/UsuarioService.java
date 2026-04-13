package chronos.tech.service;

import chronos.tech.dto.CreateUsuarioRequestDTO;
import chronos.tech.dto.UsuarioResponseDTO;
import chronos.tech.dto.mapper.UsuarioMapper;
import chronos.tech.model.classes.Usuario;
import chronos.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> pegarTodosUsuarios(){
        return repository.findAll();
    }

    public UsuarioResponseDTO criarUsuario(CreateUsuarioRequestDTO dto){

        Usuario usuario = mapper.toModel(dto);
        Usuario salvo = repository.save(usuario);

        return mapper.toResponse(salvo);

    }

    public UsuarioResponseDTO pegarUsuarioPorId(Long id){
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Não foi possível")
        );

        return mapper.toResponse(usuario);
    }

}
