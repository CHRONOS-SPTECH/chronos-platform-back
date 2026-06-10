package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.AuthLoginRequestDTO;
import chronos.tech.application.dto.request.AuthRefreshRequestDTO;
import chronos.tech.application.dto.request.AuthRegisterRequestDTO;
import chronos.tech.application.dto.response.AuthResponseDTO;

public interface AuthUseCase {
    AuthResponseDTO register(AuthRegisterRequestDTO dto);
    AuthResponseDTO login(AuthLoginRequestDTO dto);
    AuthResponseDTO refresh(AuthRefreshRequestDTO dto);
}
