package chronos.tech.application.dto.pessoa;

import chronos.tech.domain.model.enums.StatusTurma;

import java.time.LocalDate;

public record TurmaPessoaDetalheResponseDTO(
        Integer id_turma,
        String nome_turma,
        StatusTurma status_turma,
        LocalDate data_matricula,
        long total_chamadas,
        long presencas,
        double percentual_presenca
) {
}
