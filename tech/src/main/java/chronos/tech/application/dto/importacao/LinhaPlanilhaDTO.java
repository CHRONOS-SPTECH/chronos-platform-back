package chronos.tech.application.dto.importacao;

public record LinhaPlanilhaDTO(
        int numeroLinha,
        String data,
        String horario,
        String materia,
        String professor,
        String turma
) {
}