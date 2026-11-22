package chronos.tech.application.dto.home.shared;

import java.util.List;

public record HomeEventSeriesDTO(String unit, long total, List<HomeSeriesPointDTO> series) {
}


