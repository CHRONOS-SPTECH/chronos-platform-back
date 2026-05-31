package chronos.tech.application.dto.response;

import java.util.List;

public record RelatorioImportacaoResponseDTO(
        int totalProcessado,
        int totalSucesso,
        int totalFalhas,
        List<ItemRelatorioImportacaoResponseDTO> falhas // Lista detalhada do que deu errado
) {}