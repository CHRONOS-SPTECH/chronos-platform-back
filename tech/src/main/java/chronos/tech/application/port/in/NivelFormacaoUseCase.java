package chronos.tech.application.port.in;

import chronos.tech.application.dto.cadastro.NivelFormacaoRequestDTO;
import chronos.tech.application.dto.cadastro.NivelFormacaoResponseDTO;

import java.util.List;

public interface NivelFormacaoUseCase {
    List<NivelFormacaoResponseDTO> getAllNivelFormacao();

    NivelFormacaoResponseDTO getNivelFormacao(Long id);

    NivelFormacaoResponseDTO saveNivelFormacao(NivelFormacaoRequestDTO nivelFormacao);

    NivelFormacaoResponseDTO updateNivelFormacao(Long id, NivelFormacaoRequestDTO nivelFormacaoAtualizado);

    void deletNivelFormacao(Long id);
}
