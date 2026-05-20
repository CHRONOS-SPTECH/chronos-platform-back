package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.StatusAula;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record AulaComAlunosResponseDTO(
        Integer id_aula,
        String nome_aula,
        Date data_aula,
        Time hora_inicio,
        Time hora_fim,
        StatusAula statusAula,
        LocalDateTime data_criacao_registro,
        TurmaSimplificadaDTO turma,
        List<AlunoComPresencaDTO> alunos
) {
}
