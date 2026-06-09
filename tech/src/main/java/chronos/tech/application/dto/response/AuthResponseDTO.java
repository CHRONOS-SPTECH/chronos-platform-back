package chronos.tech.application.dto.response;

public record AuthResponseDTO(String token, String tokenType, String refreshToken, UsuarioResponseDTO usuario) {
}
