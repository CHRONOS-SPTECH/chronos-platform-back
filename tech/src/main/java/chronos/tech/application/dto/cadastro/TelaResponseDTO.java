package chronos.tech.application.dto.cadastro;

public record TelaResponseDTO(
        Integer id_tela,
        String codigo_tela,
        String nome_tela,
        String grupo,
        Integer ordem,
        Boolean status_ativo
) {
}

