package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.TurmaAlunoResponseDTO;
import chronos.tech.application.dto.response.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.response.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;

import java.util.List;

public interface TurmaUseCase {
    List<TurmaResponseDTO> getAllTurmas();

    TurmaResponseDTO getTurma(Long id);

    TurmaResponseDTO saveTurma(TurmaRequestDTO turma);

    TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado);

    TurmaDeletadaResponseDTO deleteTurma(Long id);

    TurmaDeletadaResponseDTO encerrarTurma(Long id);

    TurmaAlunoResponseDTO getAlunosByTurma(Long id);

    List<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId);
}
