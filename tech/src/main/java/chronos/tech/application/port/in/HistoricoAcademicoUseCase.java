package chronos.tech.application.port.in;

import chronos.tech.application.dto.historico.HistoricoAcademicoRequestDTO;
import chronos.tech.application.dto.historico.HistoricoAcademicoResponseDTO;

import java.util.List;

public interface HistoricoAcademicoUseCase {
    List<HistoricoAcademicoResponseDTO> getAllHistoricos();
    HistoricoAcademicoResponseDTO getHistorico(Long id);
    HistoricoAcademicoResponseDTO saveHistorico(HistoricoAcademicoRequestDTO dto);
    HistoricoAcademicoResponseDTO updateHistorico(Long id, HistoricoAcademicoRequestDTO dto);
    void deleteHistorico(Long id);
}
