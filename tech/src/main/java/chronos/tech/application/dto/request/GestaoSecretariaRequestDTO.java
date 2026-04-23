package chronos.tech.application.dto.request;

import chronos.tech.domain.model.enums.CargoAcesso;

public record GestaoSecretariaRequestDTO(Long id_pessoa, Long id_secretaria, CargoAcesso cargo_acesso) {
}
