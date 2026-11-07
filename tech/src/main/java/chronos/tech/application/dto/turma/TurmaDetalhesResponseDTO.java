package chronos.tech.application.dto.turma;

import chronos.tech.domain.model.enums.StatusTurma;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record TurmaDetalhesResponseDTO(
        Long id_turma,
        String nome_turma,
        LocalDate data_inicio,
        LocalDate data_encerramento,
        StatusTurma status_turma,
        Double percentual_conclusao,
        IndicadoresDTO indicadores,
        ProximaAulaDTO proxima_aula,
        ResponsavelDTO responsavel
) {
    public record IndicadoresDTO(
            long total_alunos,
            Double percentual_presenca_media,
            long aulas_realizadas,
            long aulas_planejadas
    ) {}

    public record ProximaAulaDTO(
            Integer id_aula,
            OffsetDateTime data_hora_inicio,
            OffsetDateTime data_hora_fim,
            String tema
    ) {}

    public record ResponsavelDTO(
            Long id_pessoa,
            String nome,
            String email
    ) {}
}
