package chronos.tech.application.dto.request;

import java.time.LocalDate;

public record MatriculaTurmaRequestDTO(Long id_turma, Long id_pessoa, LocalDate data_matricula) {
}
