package chronos.tech.application.dto.turma;

import java.time.LocalDate;

public record AvaliacaoFormacaoResponseDTO(
        Long id_turma,
        Long id_pessoa,
        String nome_aluno,
        String status,
        LocalDate data_decisao,
        Integer id_usuario_decisor,
        String observacao
) {}
