package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.request.ListaChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;

import java.util.List;

public interface ChamadaAulaUseCase {
    List<ChamadaAulaResponseDTO> getAllChamadas();
    ChamadaAulaResponseDTO getChamada(Long id);
    ChamadaAulaResponseDTO saveChamada(ChamadaAulaRequestDTO dto);
    ChamadaAulaResponseDTO updateChamada(Long id, ChamadaAulaRequestDTO dto);
    void deleteChamada(Long id);

    List<ChamadaAulaResponseDTO> saveListaChamada(ListaChamadaAulaRequestDTO dto);

    List<ChamadaAulaResponseDTO> getChamadasByAula(Long id_aula);
}
