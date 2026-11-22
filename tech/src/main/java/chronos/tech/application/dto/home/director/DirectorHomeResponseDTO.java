package chronos.tech.application.dto.home.director;

import chronos.tech.application.dto.home.shared.HomeEventDTO;
import chronos.tech.application.dto.home.shared.HomeEventSeriesDTO;
import chronos.tech.application.dto.home.shared.HomeItemDTO;
import chronos.tech.application.dto.home.shared.HomeMetaDTO;

import java.util.List;

public record DirectorHomeResponseDTO(HomeMetaDTO meta, DirectorHomeIndicatorsDTO indicators,
                                      List<HomeItemDTO> schoolFollowUp,
                                      List<HomeEventDTO> upcomingEvents,
                                      HomeEventSeriesDTO eventsCompletedByMonth) {
}

