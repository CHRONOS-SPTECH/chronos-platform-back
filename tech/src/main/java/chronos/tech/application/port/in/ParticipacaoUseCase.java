package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.ParticipacaoRequestDTO;
import chronos.tech.application.dto.response.ParticipacaoResponseDTO;

import java.util.List;

public interface ParticipacaoUseCase {
    List<ParticipacaoResponseDTO> getAllParticipacoes();
    ParticipacaoResponseDTO getParticipacao(Long id);
    ParticipacaoResponseDTO saveParticipacao(ParticipacaoRequestDTO dto);
    ParticipacaoResponseDTO updateParticipacao(Long id, ParticipacaoRequestDTO dto);
    void deleteParticipacao(Long id);
}
