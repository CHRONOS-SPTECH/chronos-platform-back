package chronos.tech.application.dto.cadastro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PerfilAcessoRequestDTO(
        @NotBlank @Size(max = 50) String nome_perfil,
        @Size(max = 255) String descricao,
        Boolean status_ativo,
        @Size(max = 50) String codigo_tela_inicial,
        java.util.List<Integer> telas_ids
) {
}
