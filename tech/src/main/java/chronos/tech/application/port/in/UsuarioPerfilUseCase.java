package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.UsuarioPerfilRequestDTO;
import chronos.tech.application.dto.response.UsuarioPerfilResponseDTO;

import java.util.List;

public interface UsuarioPerfilUseCase {
    List<UsuarioPerfilResponseDTO> getAllVinculos();
    UsuarioPerfilResponseDTO getVinculo(Long id);
    UsuarioPerfilResponseDTO saveVinculo(UsuarioPerfilRequestDTO dto);
    UsuarioPerfilResponseDTO updateVinculo(Long id, UsuarioPerfilRequestDTO dto);
    void deleteVinculo(Long id);
}
