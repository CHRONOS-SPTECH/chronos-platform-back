package chronos.tech.application.dto.home.instructor;

import chronos.tech.application.dto.home.shared.HomeMetaDTO;

import java.util.List;

public record InstructorHomeResponseDTO(HomeMetaDTO meta, InstructorHomeIndicatorsDTO indicators,
                                        List<HomeScheduleDTO> todaySchedule,
                                        HomeWeeklyHoursDTO weeklyTeachingHours) {
}


