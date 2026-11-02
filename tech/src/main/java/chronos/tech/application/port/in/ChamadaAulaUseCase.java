package chronos.tech.application.port.in;

import chronos.tech.application.dto.chamada.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.chamada.ListaChamadaAulaRequestDTO;
import chronos.tech.application.dto.chamada.ChamadaAulaResponseDTO;

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
