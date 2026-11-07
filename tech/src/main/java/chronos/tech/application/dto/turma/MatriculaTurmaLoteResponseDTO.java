package chronos.tech.application.dto.turma;

import java.util.List;

public record MatriculaTurmaLoteResponseDTO(
        int total_solicitado,
        int total_matriculado,
        int total_com_erro,
        List<MatriculaTurmaResponseDTO> matriculas,
        List<ErroMatriculaTurmaDTO> erros
) {
    public record ErroMatriculaTurmaDTO(Long id_pessoa, int codigo_http, String mensagem) {}
}
