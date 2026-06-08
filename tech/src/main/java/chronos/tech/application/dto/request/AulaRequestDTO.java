package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.StatusAula;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public record AulaRequestDTO(
        Date data_aula,
        Time hora_inicio,
        Time hora_fim,
        String statusAula,
        LocalDateTime data_criacao_registro,
        @NotNull
        Long id_turma,
        @NotNull
        Long id_tema,
        @NotNull
        Long id_instrutor
) {
}
