package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.SecretariaRequestDTO;
import chronos.tech.application.dto.response.SecretariaResponseDTO;

import java.util.List;

public interface SecretariaUseCase {
    List<SecretariaResponseDTO> getAllSecretaria();

    SecretariaResponseDTO getSecretaria(Long id);

    SecretariaResponseDTO saveSecretaria(SecretariaRequestDTO secretaria);

    SecretariaResponseDTO updateSecretaria(Long id, SecretariaRequestDTO secretariaAtualizado);

    void deletSecretaria(Long id);
}
