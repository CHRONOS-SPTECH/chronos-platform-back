package chronos.tech.application.dto.response.dashboard;

import java.util.List;

public record DashboardInstrutoresResponseDTO(
        Integer total_instrutores,
        List<InstrutorDTO> instrutores
) {
    public record InstrutorDTO(
            Long id, String nome, String inicial,
            String foto_url, Boolean ativo
    ) {}
}
