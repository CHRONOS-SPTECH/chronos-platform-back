package chronos.tech.application.dto.home.shared;

import java.time.OffsetDateTime;

public record HomeEventDTO(Integer id, String title, OffsetDateTime startsAt, String location, String category) {
}


