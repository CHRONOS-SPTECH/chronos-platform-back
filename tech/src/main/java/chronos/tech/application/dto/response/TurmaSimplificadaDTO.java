package chronos.tech.application.dto.response;

import java.time.LocalDate;

public record TurmaSimplificadaDTO(
        Integer id_turma,
        String nome_turma,
        LocalDate data_inicio,
        LocalDate data_encerramento
) {
}
