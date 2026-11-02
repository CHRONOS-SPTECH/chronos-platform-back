package chronos.tech.application.port.in;

import chronos.tech.application.dto.turma.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.turma.MatriculaTurmaResponseDTO;

import java.util.List;

public interface MatriculaTurmaUseCase {
    List<MatriculaTurmaResponseDTO> getAllMatriculas();
    MatriculaTurmaResponseDTO getMatricula(Integer idTurma, Integer idPessoa);
    MatriculaTurmaResponseDTO saveMatricula(MatriculaTurmaRequestDTO dto);
    MatriculaTurmaResponseDTO updateMatricula(Integer idTurma, Integer idPessoa, MatriculaTurmaRequestDTO dto);
    void deleteMatricula(Integer idTurma, Integer idPessoa);
}
