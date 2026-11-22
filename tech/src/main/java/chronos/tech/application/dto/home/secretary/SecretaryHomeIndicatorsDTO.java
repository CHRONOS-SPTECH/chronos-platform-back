package chronos.tech.application.dto.home.secretary;

public record SecretaryHomeIndicatorsDTO(long activeEnrollments, long classesInProgress,
                                         long lessonsScheduledThisWeek,
                                         long studentsAwaitingPlacement) {
}


