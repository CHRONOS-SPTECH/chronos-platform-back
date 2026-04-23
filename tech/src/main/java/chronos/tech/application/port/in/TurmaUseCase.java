package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;

import java.util.List;

public interface TurmaUseCase {
    List<TurmaResponseDTO> getAllTurmas();

    TurmaResponseDTO getTurma(Long id);

    TurmaResponseDTO saveTurma(TurmaRequestDTO turma);

    TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado);

    void deleteTurma(Long id);
}
