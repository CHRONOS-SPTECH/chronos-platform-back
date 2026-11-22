package chronos.tech.application.dto.home.instructor;

import java.time.LocalDate;
import java.util.List;

public record HomeWeeklyHoursDTO(String unit, double total, LocalDate periodStart, LocalDate periodEnd,
                                 List<HomeDayPointDTO> series) {
}


