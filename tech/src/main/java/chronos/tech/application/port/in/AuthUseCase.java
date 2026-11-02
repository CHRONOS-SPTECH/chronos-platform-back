package chronos.tech.application.port.in;

import chronos.tech.application.dto.auth.AuthLoginRequestDTO;
import chronos.tech.application.dto.auth.AuthRefreshRequestDTO;
import chronos.tech.application.dto.auth.AuthRegisterRequestDTO;
import chronos.tech.application.dto.auth.AuthResponseDTO;

public interface AuthUseCase {
    AuthResponseDTO register(AuthRegisterRequestDTO dto);
    AuthResponseDTO login(AuthLoginRequestDTO dto);
    AuthResponseDTO refresh(AuthRefreshRequestDTO dto);
}
