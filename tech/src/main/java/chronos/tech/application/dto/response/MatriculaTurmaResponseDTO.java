package chronos.tech.application.dto.response;

import java.time.LocalDate;

public record MatriculaTurmaResponseDTO(
        Long id_matricula_turma,
        Long id_turma,
        Long id_pessoa,
        LocalDate data_matricula
) {
}
