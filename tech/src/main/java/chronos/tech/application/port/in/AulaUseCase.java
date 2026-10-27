package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.request.MovimentacaoAulaDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaComInstrutorResponseDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaResponseDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.dto.response.RelatorioImportacaoResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AulaUseCase {
    List<AulaResponseDTO> getAllAulas();
    Page<AulaResponseDTO> getAllAulas(Pageable pageable);
    AulaResponseDTO getAula(Integer id);
    AulaResponseDTO saveAula(AulaRequestDTO dto);
    AulaResponseDTO updateAula(Integer id, AulaRequestDTO dto);
    void deleteAula(Integer id);

    List<AulaComTemaEMateriaResponseDTO> getAulasDoDia(Date data, Integer instrutorId);
    Page<AulaComTemaEMateriaResponseDTO> getAulasDoDia(Date data, Integer instrutorId, Pageable pageable);
    AulaComTemaEMateriaResponseDTO getAulaComTemaEMateriaPorId(Integer id);

    List<AulaComTemaEMateriaComInstrutorResponseDTO> getAllAulasDetails();
    Page<AulaComTemaEMateriaComInstrutorResponseDTO> getAllAulasDetails(Pageable pageable);

    List<AulaComTemaEMateriaComInstrutorResponseDTO> getAulasPorTurma(Integer idTurma);
    Page<AulaComTemaEMateriaComInstrutorResponseDTO> getAulasPorTurma(Integer idTurma, Pageable pageable);

    RelatorioImportacaoResponseDTO importarCronograma(MultipartFile file);

    void remanejarAulasEmLote(List<MovimentacaoAulaDTO> movimentacoes);
}
