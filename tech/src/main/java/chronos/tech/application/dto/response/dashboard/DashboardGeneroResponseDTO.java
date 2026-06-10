package chronos.tech.application.dto.response.dashboard;

import java.util.List;

public record DashboardGeneroResponseDTO(
        List<GeneroDTO> distribuicao_genero,
        EquilibrioDTO equilibrio_genero
) {
    public record GeneroDTO(String genero, Long total, Long percentual) {}
    public record EquilibrioDTO(
            String predominancia, Long percentual_predominante, String nota
    ) {}
}