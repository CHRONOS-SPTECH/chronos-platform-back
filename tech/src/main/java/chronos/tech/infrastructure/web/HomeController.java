package chronos.tech.infrastructure.web;

import chronos.tech.application.dto.home.director.DirectorHomeResponseDTO;
import chronos.tech.application.dto.home.instructor.InstructorHomeResponseDTO;
import chronos.tech.application.dto.home.secretary.SecretaryHomeResponseDTO;
import chronos.tech.application.service.home.DirectorHomeService;
import chronos.tech.application.service.home.InstructorHomeService;
import chronos.tech.application.service.home.SecretaryHomeService;
import chronos.tech.infrastructure.security.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Tag(name = "Homes", description = "Resumos agregados por perfil")
@SecurityRequirement(name = "bearerAuth")
public class HomeController {
    private final DirectorHomeService directorHomeService;
    private final SecretaryHomeService secretaryHomeService;
    private final InstructorHomeService instructorHomeService;

    @GetMapping("/diretor")
    @PreAuthorize("hasAnyRole('DIRETOR', 'ADMINISTRADOR')")
    public ResponseEntity<DirectorHomeResponseDTO> diretor() {
        return ResponseEntity.ok(directorHomeService.build());
    }

    @GetMapping("/secretaria")
    @PreAuthorize("hasAnyRole('SECRETÁRIO', 'ADMINISTRADOR')")
    public ResponseEntity<SecretaryHomeResponseDTO> secretaria() {
        return ResponseEntity.ok(secretaryHomeService.build());
    }

    @GetMapping("/instrutor")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ADMINISTRADOR')")
    public ResponseEntity<InstructorHomeResponseDTO> instrutor(@AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(instructorHomeService.build(principal));
    }
}
