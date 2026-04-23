package chronos.tech.application.dto.response;

public record AuthResponseDTO(String token, String token_type, UsuarioResponseDTO usuario) {
}
