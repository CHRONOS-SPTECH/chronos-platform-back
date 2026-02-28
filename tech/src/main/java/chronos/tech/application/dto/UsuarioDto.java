package chronos.tech.application.dto;

import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.model.classes.Pessoa;

import java.time.LocalDateTime;

public record UsuarioDto(
        Long id_usuario,
        Pessoa id_pessoa,
        PerfilAcesso id_perfil,
        String email_login,
        String senha_hash,
        Boolean status_ativo,
        LocalDateTime data_criacao
) {

}
