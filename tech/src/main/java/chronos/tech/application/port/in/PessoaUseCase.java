package chronos.tech.application.port.in;

import chronos.tech.application.dto.request.PessoaRegistroRequestDTO;
import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaUseCase {
    List<PessoaDetalhadaResponseDTO> getAllPersonsDetails ();

    PessoaDetalhadaResponseDTO getPersonsDetailsId (Long id);

    PessoaResponseDTO createPessoa(PessoaRequestDTO requestPessoaDto);

    PessoaResponseDTO pegarPorId(Long id);

    List<PessoaResponseDTO> getAllPersons();
    Page<PessoaResponseDTO> getAllAlunos(Pageable pageable);

    PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaAtualizado);

    void deletePessoa(Long id);

    PessoaResponseDTO createPessoaComBiometria(PessoaRegistroRequestDTO requestDto);
}

