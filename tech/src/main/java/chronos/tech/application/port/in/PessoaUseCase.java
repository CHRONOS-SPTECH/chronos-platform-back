package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;

import java.util.List;

public interface PessoaUseCase {
    List<PessoaDetalhadaResponseDTO> getAllPersonsDetails ();

    PessoaResponseDTO createPessoa(PessoaRequestDTO requestPessoaDto);

    PessoaResponseDTO pegarPorId(Long id);

    List<PessoaResponseDTO> getAllPersons();

    PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaAtualizado);

    void deletePessoa(Long id);
}
