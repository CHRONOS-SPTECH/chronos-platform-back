package chronos.tech.application.service;

import chronos.tech.application.dto.request.NivelFormacaoRequestDTO;
import chronos.tech.application.dto.response.NivelFormacaoResponseDTO;
import chronos.tech.application.mapper.NivelFormacaoMapper;
import chronos.tech.application.port.in.NivelFormacaoUseCase;
import chronos.tech.domain.model.classes.NivelFormacao;
import chronos.tech.domain.port.NivelFormacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelFormacaoService implements NivelFormacaoUseCase {

    private final NivelFormacaoRepository repository;
    private final NivelFormacaoMapper mapper;

    //Método para pegar todos os nivelFormacao
    @Override
    public List<NivelFormacaoResponseDTO> getAllNivelFormacao(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar um nivelFormacao específico
    @Override
    public NivelFormacaoResponseDTO getNivelFormacao(Long id){
        NivelFormacao nivel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NivelFormacao não encontrado com o ID: " + id));
        return mapper.toResponse(nivel);
    }

    //Salvar no banco de dados h2
    @Override
    public NivelFormacaoResponseDTO saveNivelFormacao(NivelFormacaoRequestDTO nivelFormacao){
        NivelFormacao entidade = mapper.toModel(nivelFormacao);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar o nivelFormacao
    @Override
    public NivelFormacaoResponseDTO updateNivelFormacao(Long id, NivelFormacaoRequestDTO nivelFormacaoAtualizado){
        NivelFormacao nivelFormacaoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NivelFormacao não encontrado com o ID: " + id));

        mapper.updateFromDto(nivelFormacaoAtualizado, nivelFormacaoExistente);
        return mapper.toResponse(repository.save(nivelFormacaoExistente));
    }

    //Deletar um nivelFormacao
    @Override
    public void deletNivelFormacao(Long id){
        repository.deleteById(id);
    }

}
