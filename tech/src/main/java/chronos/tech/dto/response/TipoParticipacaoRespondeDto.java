package chronos.tech.dto.response;

import chronos.tech.model.enums.TipoHoraGerada;

public record TipoParticipacaoRespondeDto(String descricao, TipoHoraGerada tipo_hora_gerada) {
}
