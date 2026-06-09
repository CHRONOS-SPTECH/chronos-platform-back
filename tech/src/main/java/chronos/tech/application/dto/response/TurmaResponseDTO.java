package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.StatusTurma;

import java.time.LocalDate;

public record TurmaResponseDTO(
        Integer id_turma,
        String nome_turma,
        LocalDate data_inicio,
        LocalDate data_encerramento,
        StatusTurma status_turma,
        Double percentual_conclusao
) {
}
