package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.TipoHoraGerada;

public record TipoParticipacaoResponseDTO(String descricao, TipoHoraGerada tipo_hora_gerada) {
}
