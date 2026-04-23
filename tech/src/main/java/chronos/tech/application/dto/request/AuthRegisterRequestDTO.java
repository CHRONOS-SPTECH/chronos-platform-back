package chronos.tech.application.dto.request;

public record AuthRegisterRequestDTO(
        PessoaRequestDTO pessoa,
        Long perfil_id,
        String email_login,
        String senha
) {
}
