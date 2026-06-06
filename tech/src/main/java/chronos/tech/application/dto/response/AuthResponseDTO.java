package chronos.tech.application.dto.response;

import java.util.List;

public record AuthResponseDTO(String token, String token_type, UsuarioResponseDTO usuario, List<PerfilAcessoResponseDTO> perfis) {
}
