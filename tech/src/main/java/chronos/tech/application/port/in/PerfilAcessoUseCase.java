package chronos.tech.application.port.in;

import chronos.tech.application.dto.cadastro.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.cadastro.PerfilAcessoResponseDTO;

import java.util.List;

public interface PerfilAcessoUseCase {
    List<PerfilAcessoResponseDTO> getAllPerfilAcesso();

    PerfilAcessoResponseDTO getPerfilAcesso(Long id);

    PerfilAcessoResponseDTO savePerfilAcesso(PerfilAcessoRequestDTO perfilAcesso);

    PerfilAcessoResponseDTO updatePerfilAcesso(Long id, PerfilAcessoRequestDTO perfilAcessoAtualizado);

    void deletPerfilAcesso(Long id);
}
