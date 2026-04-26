package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.TipoVinculoRequestDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;

import java.util.List;

public interface TipoVinculoUseCase {
    List<TipoVinculoResponseDTO> getAllTiposVinculo();
    TipoVinculoResponseDTO getTipoVinculo(Integer id);
    TipoVinculoResponseDTO saveTipoVinculo(TipoVinculoRequestDTO dto);
    TipoVinculoResponseDTO updateTipoVinculo(Integer id, TipoVinculoRequestDTO dto);
    void deleteTipoVinculo(Integer id);
}
