package chronos.tech.application.dto.response;

public record NivelFormacaoResponseDTO(
        Integer id_nivel,
        String nome_nivel,
        Integer ordem_hierarquia
) {
}
