package chronos.tech.application.dto.turma;

import java.time.LocalDate;

public record MatriculaTurmaResponseDTO(
        Long id_turma,
        Long id_pessoa,
        LocalDate data_matricula
) {
}
