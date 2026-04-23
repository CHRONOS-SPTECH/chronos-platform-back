package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;

import java.util.List;

public interface MatriculaTurmaUseCase {
    List<MatriculaTurmaResponseDTO> getAllMatriculas();
    MatriculaTurmaResponseDTO getMatricula(Long id);
    MatriculaTurmaResponseDTO saveMatricula(MatriculaTurmaRequestDTO dto);
    MatriculaTurmaResponseDTO updateMatricula(Long id, MatriculaTurmaRequestDTO dto);
    void deleteMatricula(Long id);
}
