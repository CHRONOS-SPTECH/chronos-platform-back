package chronos.tech.application.dto.chamada;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ListaChamadaAulaRequestDTO(
        @NotNull Integer id_aula,
        @NotEmpty List<ChamadaAlunoRequestDTO> alunos
) {
}