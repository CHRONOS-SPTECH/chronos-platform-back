package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.AuthLoginRequestDTO;
import chronos.tech.application.dto.request.AuthRegisterRequestDTO;
import chronos.tech.application.dto.response.AuthResponseDTO;
import chronos.tech.application.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Validated AuthRegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authUseCase.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Validated AuthLoginRequestDTO dto) {
        return ResponseEntity.ok(authUseCase.login(dto));
    }
}
