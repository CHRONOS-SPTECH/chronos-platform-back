package chronos.tech.application.dto.pessoa;

import chronos.tech.domain.model.enums.StatusMateria;

import java.time.LocalDate;

public record HistoricoAcademicoDetalheResponseDTO(
        Integer id_historico,
        Integer id_materia,
        String materia,
        Integer id_nivel,
        String nivel,
        Integer ordem_hierarquia,
        StatusMateria status_materia,
        LocalDate data_status
) {
}
