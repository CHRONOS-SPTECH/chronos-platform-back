package chronos.tech.application.port.in;

import chronos.tech.application.dto.pessoa.PessoaRegistroRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaBiometriaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaFichaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaResponseDTO;
import chronos.tech.application.dto.pessoa.AlunoListaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaPublicoExternoListaResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaUseCase {
    Page<PessoaDetalhadaResponseDTO> getAllPersonsDetails(Pageable pageable);

    PessoaFichaResponseDTO getPersonsDetailsId(Long id);

    PessoaResponseDTO createPessoa(PessoaRequestDTO requestPessoaDto);

    PessoaResponseDTO pegarPorId(Long id);

    Page<PessoaPublicoExternoListaResponseDTO> getAllPersons(Pageable pageable);
    Page<AlunoListaResponseDTO> getAllAlunos(Pageable pageable);

    PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaAtualizado);

    void deletePessoa(Long id);

    void inativarPessoa(Long id);

    void reativarPessoa(Long id);

    PessoaResponseDTO atualizarBiometria(Long id, PessoaBiometriaRequestDTO request);

    void removerBiometria(Long id);

    PessoaResponseDTO createPessoaComBiometria(PessoaRegistroRequestDTO requestDto);
}

