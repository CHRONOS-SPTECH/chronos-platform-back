package chronos.tech.application.dto.response;

import java.time.LocalDate;

public record UsuarioResponseDTO(
        Integer id_usuario,
        PessoaResponseDTO pessoa,
        String email_login,
        Boolean status_ativo,
        LocalDate data_criacao
) {
}
