package chronos.tech.application.dto.request;


public record UsuarioRequestDTO(PessoaRequestDTO pessoa, PerfilAcessoRequestDTO perfilAcesso, String email_login, String senha_hash){
}