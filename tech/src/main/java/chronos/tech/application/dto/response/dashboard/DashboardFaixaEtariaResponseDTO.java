package chronos.tech.application.dto.response.dashboard;

import java.util.List;

public record DashboardFaixaEtariaResponseDTO(
        List<FaixaDTO> faixas,
        ResumoFaixaDTO resumo
) {
    public record FaixaDTO(String faixa, Long mulheres, Long homens) {}
    public record ResumoFaixaDTO(
            String idade_mais_comum, Integer media_anos, Long total_acima_65
    ) {}
}
