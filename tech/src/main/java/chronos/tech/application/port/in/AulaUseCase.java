package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaComInstrutorResponseDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaResponseDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.dto.response.RelatorioImportacaoResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public interface AulaUseCase {
    List<AulaResponseDTO> getAllAulas();
    AulaResponseDTO getAula(Integer id);
    AulaResponseDTO saveAula(AulaRequestDTO dto);
    AulaResponseDTO updateAula(Integer id, AulaRequestDTO dto);
    void deleteAula(Integer id);

    List<AulaComTemaEMateriaResponseDTO> getAulasDoDia(Date data, Integer instrutorId);
    AulaComTemaEMateriaResponseDTO getAulaComTemaEMateriaPorId(Integer id);

    List<AulaComTemaEMateriaComInstrutorResponseDTO> getAulasPorTurma(Integer idTurma);

    RelatorioImportacaoResponseDTO importarCronograma(MultipartFile file);
}