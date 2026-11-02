package chronos.tech.application.port.in;

import chronos.tech.application.dto.cadastro.GestaoSecretariaRequestDTO;
import chronos.tech.application.dto.cadastro.GestaoSecretariaResponseDTO;

import java.util.List;

public interface GestaoSecretariaUseCase {
    List<GestaoSecretariaResponseDTO> getAllGestoes();
    GestaoSecretariaResponseDTO getGestao(Long id);
    GestaoSecretariaResponseDTO saveGestao(GestaoSecretariaRequestDTO dto);
    GestaoSecretariaResponseDTO updateGestao(Long id, GestaoSecretariaRequestDTO dto);
    void deleteGestao(Long id);
}
