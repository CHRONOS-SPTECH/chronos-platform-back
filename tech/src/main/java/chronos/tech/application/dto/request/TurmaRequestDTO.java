package chronos.tech.application.dto.request;

import java.time.LocalDate;

public record TurmaRequestDTO(String nome_turma, LocalDate data_inicio, LocalDate data_encerramento, Boolean status_encerrado) {
}
