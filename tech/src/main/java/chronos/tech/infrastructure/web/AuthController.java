package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.request.AuthLoginRequestDTO;
import chronos.tech.application.dto.request.AuthRegisterRequestDTO;
import chronos.tech.application.dto.response.AuthResponseDTO;
import chronos.tech.application.port.in.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthController {

    private final AuthUseCase authUseCase;

    @Operation(
            summary = "Registrar usuário",
            description = "Cria um novo usuário no sistema"
    )
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody @Validated AuthRegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authUseCase.register(dto));
    }

    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna token"
    )
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Validated AuthLoginRequestDTO dto) {
        return ResponseEntity.ok(authUseCase.login(dto));
    }
}