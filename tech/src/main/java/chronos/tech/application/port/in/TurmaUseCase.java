package chronos.tech.application.port.in;

import chronos.tech.application.dto.turma.TurmaRequestDTO;
import chronos.tech.application.dto.turma.TurmaAlunoResponseDTO;
import chronos.tech.application.dto.turma.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.pessoa.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.turma.TurmaResponseDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import chronos.tech.application.dto.turma.TurmaAlunoPaginaResponseDTO;

public interface TurmaUseCase {
    List<TurmaResponseDTO> getAllTurmas();

    TurmaResponseDTO getTurma(Long id);

    TurmaResponseDTO saveTurma(TurmaRequestDTO turma);

    TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado);

    TurmaDeletadaResponseDTO deleteTurma(Long id);

    TurmaDeletadaResponseDTO encerrarTurma(Long id);

    TurmaAlunoResponseDTO getAlunosByTurma(Long id);
    TurmaAlunoPaginaResponseDTO getAlunosByTurma(Long id, Pageable pageable);

    List<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId);
    Page<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId, Pageable pageable);
}
