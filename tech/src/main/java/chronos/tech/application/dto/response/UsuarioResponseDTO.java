package chronos.tech.application.dto.response;

public record UsuarioResponseDTO (PessoaResponseDTO pessoa, PerfilAcessoResponseDTO perfilAcesso, String email_login, Boolean status_ativo) {
}
