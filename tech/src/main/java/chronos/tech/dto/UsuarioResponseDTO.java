package chronos.tech.dto;

import chronos.tech.dto.response.PessoaResponseDto;

public record UsuarioResponseDTO (PessoaResponseDto pessoa, PerfilAcessoResponseDTO perfilAcesso) {
}
