package chronos.tech.application.dto.auth;

import chronos.tech.application.dto.cadastro.PerfilAcessoResponseDTO;
import chronos.tech.application.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public record AuthResponseDTO(String token, String tokenType, String refreshToken, UsuarioResponseDTO usuario, List<PerfilAcessoResponseDTO> perfis) {
}
