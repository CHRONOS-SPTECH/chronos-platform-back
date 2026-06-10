package chronos.tech.application.dto.response;

import java.util.List;

public record TurmaAlunoResponseDTO(
        Long id_turma,
        String nome_turma,
        Integer total_alunos,
        List<AlunoResumoDTO> alunos
) {
    public record AlunoResumoDTO(
            Long id_pessoa,
            String nome,
            String email,
            String status_pessoa
    ) {}
}