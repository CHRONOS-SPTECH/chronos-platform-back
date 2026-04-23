package chronos.tech.application.dto.response;

import java.time.LocalDate;

public record TurmaResponseDTO(String nome_turma, LocalDate data_inicio, LocalDate data_encerramento, Boolean status_encerrado) {
}
