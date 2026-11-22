package chronos.tech.application.dto.home.secretary;

import chronos.tech.application.dto.home.shared.HomeClassDTO;

import java.time.OffsetDateTime;

public record UnscheduledLessonDTO(Integer lessonId,
                                   String subject,
                                   OffsetDateTime startsAt,
                                   OffsetDateTime endsAt,
                                   HomeClassDTO turma,
                                   String room,
                                   UnscheduledInstructorDTO instructor) {
}

