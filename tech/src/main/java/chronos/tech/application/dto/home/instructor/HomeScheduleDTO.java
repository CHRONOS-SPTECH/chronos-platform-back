package chronos.tech.application.dto.home.instructor;

import chronos.tech.application.dto.home.shared.HomeClassDTO;

import java.time.OffsetDateTime;

public record HomeScheduleDTO(Integer lessonId, OffsetDateTime startsAt, OffsetDateTime endsAt,
                              String subject, HomeClassDTO turma, String room, String attendanceStatus) {
}


