package chronos.tech.application.dto.home.instructor;

public record HomeWeeklyLessonsDTO(long total, long lessonsMarkedRealized, long attendancePending,
                                   long inProgress, long notStarted) {
}


