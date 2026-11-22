package chronos.tech.application.dto.home.secretary;

import chronos.tech.application.dto.home.shared.HomeEventDTO;
import chronos.tech.application.dto.home.shared.HomeItemDTO;
import chronos.tech.application.dto.home.shared.HomeMetaDTO;

import java.util.List;

public record SecretaryHomeResponseDTO(HomeMetaDTO meta, SecretaryHomeIndicatorsDTO indicators,
                                       List<HomeItemDTO> operationalQueue,
                                       List<UnscheduledLessonDTO> unscheduledLessons,
                                       List<HomeEventDTO> upcomingEvents) {
}
