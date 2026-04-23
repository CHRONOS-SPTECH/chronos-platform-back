package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.StatusMateria;

import java.time.LocalDate;

public record HistoricoAcademicoResponseDTO(Long id_historico, Long id_pessoa, Long id_materia, StatusMateria status_materia, LocalDate data_status) {
}
