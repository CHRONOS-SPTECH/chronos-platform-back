package chronos.tech.application.dto.response;

import java.util.List;

public record AuthResponseDTO(String token, String tokenType, String refreshToken, UsuarioResponseDTO usuario, List<PerfilAcessoResponseDTO> perfis) {
}
