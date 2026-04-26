package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.StatusAula;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public record AulaResponseDTO(
        Integer id_aula,
        Date data_aula,
        Time hora_inicio,
        Time hora_fim,
        StatusAula statusAula,
        LocalDateTime data_criacao_registro,
        Long id_turma,
        Long id_tema
) {
}
