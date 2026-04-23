package chronos.tech.application.dto.response;

import chronos.tech.domain.model.enums.CargoAcesso;

public record GestaoSecretariaResponseDTO(Long id_gestao_secretaria, Long id_pessoa, Long id_secretaria, CargoAcesso cargo_acesso) {
}
