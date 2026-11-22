package chronos.tech.application.dto.home.shared;

import java.time.OffsetDateTime;

public record HomeLessonDTO(Integer id, OffsetDateTime startsAt, HomeClassDTO turma, String subject) {
}


