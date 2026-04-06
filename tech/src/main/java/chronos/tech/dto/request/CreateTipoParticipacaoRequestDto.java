package chronos.tech.dto.request;

import chronos.tech.model.enums.TipoHoraGerada;

public record CreateTipoParticipacaoRequestDto(String descricao, TipoHoraGerada tipo_hora_gerada) {
}
