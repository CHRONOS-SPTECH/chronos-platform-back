package chronos.tech.dto.response;

import java.time.LocalDate;

public record TurmaResponseDto(String nome_turma, LocalDate data_inicio, LocalDate data_encerramento, Boolean status_encerrado) {
}
