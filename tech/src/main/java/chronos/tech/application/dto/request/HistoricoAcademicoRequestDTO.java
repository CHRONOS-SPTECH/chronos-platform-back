package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.StatusMateria;

import java.time.LocalDate;

public record HistoricoAcademicoRequestDTO(Long id_pessoa, Long id_materia, StatusMateria status_materia, LocalDate data_status) {
}
