package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.TipoParticipacaoRequestDTO;
import chronos.tech.application.dto.response.TipoParticipacaoResponseDTO;

import java.util.List;

public interface TipoParticipacaoUseCase {
    List<TipoParticipacaoResponseDTO> getAllTipoParticipacao();

    TipoParticipacaoResponseDTO getTipoParticipacao(Long id);

    TipoParticipacaoResponseDTO saveTipoParticipacao(TipoParticipacaoRequestDTO tipoParticipacao);

    TipoParticipacaoResponseDTO updateTipoParticipacao(Long id, TipoParticipacaoRequestDTO tipoParticipacaoAtualizado);

    void deleteTipoParticipacao(Long id);
}
