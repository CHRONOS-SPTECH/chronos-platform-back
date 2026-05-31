package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.StatusAula;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public record AulaRequestDTO(
        @NotNull
        Date data_aula,
        @NotNull
        Time hora_inicio,
        @NotNull
        Time hora_fim,
        StatusAula statusAula,
        LocalDateTime data_criacao_registro,
        @NotNull
        Long id_turma,
        @NotNull
        Long id_tema,
        @NotNull
        Long id_instrutor
) {
}
