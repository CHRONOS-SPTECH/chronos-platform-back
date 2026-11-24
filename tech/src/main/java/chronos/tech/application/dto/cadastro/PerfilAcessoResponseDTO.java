package chronos.tech.application.dto.cadastro;

public record PerfilAcessoResponseDTO(
        Integer id_perfil,
        String nome_perfil,
        String descricao,
        Boolean status_ativo,
        String codigo_tela_inicial,
        java.util.List<TelaResponseDTO> telas
) {
}
