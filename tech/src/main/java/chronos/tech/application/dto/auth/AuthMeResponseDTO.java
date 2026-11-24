package chronos.tech.application.dto.auth;

import chronos.tech.application.dto.cadastro.PerfilAcessoResponseDTO;
import chronos.tech.application.dto.cadastro.TelaResponseDTO;
import chronos.tech.application.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public record AuthMeResponseDTO(
        UsuarioResponseDTO usuario,
        List<PerfilAcessoResponseDTO> perfis,
        List<TelaResponseDTO> telasPermitidas
) {
}

