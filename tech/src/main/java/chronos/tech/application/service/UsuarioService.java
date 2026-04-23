package chronos.tech.application.service;

import chronos.tech.application.dto.request.UsuarioRequestDTO;
import chronos.tech.application.dto.response.UsuarioResponseDTO;
import chronos.tech.application.mapper.UsuarioMapper;
import chronos.tech.application.port.in.UsuarioUseCase;
import chronos.tech.domain.model.classes.Usuario;
import chronos.tech.domain.port.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;

    @Override
    public List<UsuarioResponseDTO> pegarTodosUsuarios(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto){

        Usuario usuario = mapper.toModel(dto);
        Usuario salvo = repository.save(usuario);

        return mapper.toResponse(salvo);

    }

    @Override
    public UsuarioResponseDTO pegarUsuarioPorId(Long id){
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Não foi possível")
        );

        return mapper.toResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado com o ID: " + id)
        );

        mapper.updateFromDto(dto, usuarioExistente);
        Usuario atualizado = repository.save(usuarioExistente);
        return mapper.toResponse(atualizado);
    }

    @Override
    public void deletarUsuario(Long id) {
        repository.deleteById(id);
    }

}
