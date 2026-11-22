package chronos.tech.application.dto.home.shared;

import java.util.List;

public record HomeItemDTO(String type, long count, List<Integer> relatedIds) {
}


