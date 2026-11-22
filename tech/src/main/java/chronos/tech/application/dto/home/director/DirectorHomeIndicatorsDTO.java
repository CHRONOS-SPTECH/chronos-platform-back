package chronos.tech.application.dto.home.director;

public record DirectorHomeIndicatorsDTO(long activeStudents, long activeClasses,
                                        long upcomingEventsNext30Days,
                                        long studentsWithRecurringAbsences) {
}


