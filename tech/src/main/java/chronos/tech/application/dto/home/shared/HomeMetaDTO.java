package chronos.tech.application.dto.home.shared;

import java.time.OffsetDateTime;

public record HomeMetaDTO(String profile, OffsetDateTime generatedAt, String timeZone, Integer instructorId) {
    public HomeMetaDTO(String profile, OffsetDateTime generatedAt, String timeZone) {
        this(profile, generatedAt, timeZone, null);
    }
}


