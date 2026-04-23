package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.UsuarioRequestDTO;
import chronos.tech.application.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioUseCase {
    List<UsuarioResponseDTO> pegarTodosUsuarios();

    UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto);

    UsuarioResponseDTO pegarUsuarioPorId(Long id);

    UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto);

    void deletarUsuario(Long id);
}
