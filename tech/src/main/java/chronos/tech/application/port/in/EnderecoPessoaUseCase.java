package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.EnderecoPessoaRequestDTO;
import chronos.tech.application.dto.response.EnderecoPessoaResponseDTO;

import java.util.List;

public interface EnderecoPessoaUseCase {
    List<EnderecoPessoaResponseDTO> getAllEnderecos();
    EnderecoPessoaResponseDTO getEndereco(Integer id);
    EnderecoPessoaResponseDTO saveEndereco(EnderecoPessoaRequestDTO dto);
    EnderecoPessoaResponseDTO updateEndereco(Integer id, EnderecoPessoaRequestDTO dto);
    void deleteEndereco(Integer id);
}
