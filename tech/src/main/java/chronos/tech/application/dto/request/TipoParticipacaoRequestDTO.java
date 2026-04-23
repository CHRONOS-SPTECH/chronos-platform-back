package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.TipoHoraGerada;

public record TipoParticipacaoRequestDTO(String descricao, TipoHoraGerada tipo_hora_gerada) {
}
