package chronos.tech.application.service;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.port.in.PessoaUseCase;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.port.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService implements PessoaUseCase {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    @Override
    public List<PessoaDetalhadaResponseDTO> getAllPersonsDetails() {

        return repository.findAll()
                .stream()
                .map(this::toDetalhadaResponse)
                .toList();
    }

    @Override
    public PessoaResponseDTO createPessoa(PessoaRequestDTO requestPessoaDto) {
        Pessoa pessoa = mapper.toModel(requestPessoaDto);
        Pessoa pessoaSave = repository.save(pessoa);
        return mapper.toResponse(pessoaSave);

    }

    @Override
    public PessoaResponseDTO pegarPorId(Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível achar"));

        return mapper.toResponse(pessoa);

    }

    @Override
    public List<PessoaResponseDTO> getAllPersons() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaAtualizado){
        Pessoa pessoaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com o ID: " + id));

        mapper.updateFromDto(pessoaAtualizado, pessoaExistente);
        Pessoa pessoaAtualizada = repository.save(pessoaExistente);
        return mapper.toResponse(pessoaAtualizada);
    }

    @Override
    public void deletePessoa(Long id){
        repository.deleteById(id);
    }

    private PessoaDetalhadaResponseDTO toDetalhadaResponse(Pessoa pessoa) {

        TipoVinculoResponseDTO vinculoDTO =
                new TipoVinculoResponseDTO(
                        pessoa.getTipoVinculo().getIdTipoVinculo(),
                        pessoa.getTipoVinculo().getNome_vinculo(),
                        pessoa.getTipoVinculo().getDescricao()

                );

        return new PessoaDetalhadaResponseDTO(
                pessoa.getIdPessoa(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.getGenero(),
                pessoa.getCpf(),
                pessoa.getBolsista(),
                pessoa.getUrlFotoPerfil(),
                pessoa.getDataNascimento(),
                pessoa.getDataIngresso(),
                pessoa.getDataMembro(),
                pessoa.getDataSaida(),
                vinculoDTO
        );
    }
}
