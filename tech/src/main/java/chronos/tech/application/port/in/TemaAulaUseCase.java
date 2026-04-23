package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.TemaAulaRequestDTO;
import chronos.tech.application.dto.response.TemaAulaResponseDTO;

import java.util.List;

public interface TemaAulaUseCase {
    List<TemaAulaResponseDTO> getAllTemas();
    TemaAulaResponseDTO getTema(Long id);
    TemaAulaResponseDTO saveTema(TemaAulaRequestDTO tema);
    TemaAulaResponseDTO updateTema(Long id, TemaAulaRequestDTO tema);
    void deleteTema(Long id);
}
