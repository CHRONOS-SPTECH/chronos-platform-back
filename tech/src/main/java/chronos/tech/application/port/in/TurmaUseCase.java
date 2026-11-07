package chronos.tech.application.port.in;

import chronos.tech.application.dto.turma.TurmaRequestDTO;
import chronos.tech.application.dto.turma.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.pessoa.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.turma.TurmaResponseDTO;
import chronos.tech.application.dto.turma.AvaliacaoFormacaoResponseDTO;
import chronos.tech.application.dto.turma.DecisaoFormacaoRequestDTO;
import chronos.tech.application.dto.turma.AlunoDisponivelMatriculaResponseDTO;
import chronos.tech.application.dto.turma.TurmaDetalhesResponseDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import chronos.tech.application.dto.turma.TurmaAlunoPaginaResponseDTO;

public interface TurmaUseCase {
    List<TurmaResponseDTO> getAllTurmas();
    Page<TurmaResponseDTO> getAllTurmas(Pageable pageable);

    TurmaResponseDTO getTurma(Long id);

    TurmaResponseDTO saveTurma(TurmaRequestDTO turma);

    TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado);

    TurmaDeletadaResponseDTO deleteTurma(Long id);
    TurmaDeletadaResponseDTO iniciarTurma(Long id);

    TurmaDeletadaResponseDTO encerrarTurma(Long id);

    TurmaAlunoPaginaResponseDTO getAlunosByTurma(Long id, Pageable pageable);

    Page<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId, Pageable pageable);
    Page<AlunoDisponivelMatriculaResponseDTO> getAlunosDisponiveis(Long turmaId, Pageable pageable);
    TurmaDetalhesResponseDTO getDetalhes(Long turmaId);
    Page<AvaliacaoFormacaoResponseDTO> listarFormandos(Long idTurma, Pageable pageable);
    AvaliacaoFormacaoResponseDTO decidirFormacao(Long idTurma, Long idPessoa, DecisaoFormacaoRequestDTO decisao);
}
