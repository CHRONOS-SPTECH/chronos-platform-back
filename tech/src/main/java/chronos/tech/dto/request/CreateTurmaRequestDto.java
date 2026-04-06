package chronos.tech.dto.request;

import java.time.LocalDate;

public record CreateTurmaRequestDto(String nome_turma, LocalDate data_inicio, LocalDate data_encerramento, Boolean status_encerrado) {
}
