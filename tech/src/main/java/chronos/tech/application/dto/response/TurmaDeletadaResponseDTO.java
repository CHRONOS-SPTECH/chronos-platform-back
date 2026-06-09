package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.StatusTurma;

public record TurmaDeletadaResponseDTO (String mensagem, Long id, StatusTurma status_turma) {
}
