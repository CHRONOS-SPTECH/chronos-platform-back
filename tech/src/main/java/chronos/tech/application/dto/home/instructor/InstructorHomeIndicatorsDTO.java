package chronos.tech.application.dto.home.instructor;

public record InstructorHomeIndicatorsDTO(HomeWeeklyLessonsDTO lessonsScheduledThisWeek,
                                          long lessonsToday, long activeClasses,
                                          long studentsInClasses) {
}


