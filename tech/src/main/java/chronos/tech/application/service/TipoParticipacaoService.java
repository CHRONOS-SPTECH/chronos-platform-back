package chronos.tech.application.service;

import chronos.tech.application.dto.request.TipoParticipacaoRequestDTO;
import chronos.tech.application.dto.response.TipoParticipacaoResponseDTO;
import chronos.tech.application.mapper.TipoParticipacaoMapper;
import chronos.tech.application.port.in.TipoParticipacaoUseCase;
import chronos.tech.domain.model.classes.TipoParticipacao;
import chronos.tech.domain.port.TipoParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoParticipacaoService implements TipoParticipacaoUseCase {

    private final TipoParticipacaoRepository repository;
    private final TipoParticipacaoMapper mapper;

    //Método para pegar todos os tipoParticipacao
    @Override
    public List<TipoParticipacaoResponseDTO> getAllTipoParticipacao(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar um tipoParticipacao específico
    @Override
    public TipoParticipacaoResponseDTO getTipoParticipacao(Long id){
        TipoParticipacao tipoParticipacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoParticipacao não encontrado com o ID: " + id));
        return mapper.toResponse(tipoParticipacao);
    }

    //Salvar no banco de dados h2
    @Override
    public TipoParticipacaoResponseDTO saveTipoParticipacao(TipoParticipacaoRequestDTO tipoParticipacao){
        TipoParticipacao entidade = mapper.toModel(tipoParticipacao);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar o tipoParticipacao
    @Override
    public TipoParticipacaoResponseDTO updateTipoParticipacao(Long id, TipoParticipacaoRequestDTO tipoParticipacaoAtualizado){
        TipoParticipacao tipoParticipacaoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoParticipacao não encontrado com o ID: " + id));

        mapper.updateFromDto(tipoParticipacaoAtualizado, tipoParticipacaoExistente);
        return mapper.toResponse(repository.save(tipoParticipacaoExistente));
    }

    //Deletar um tipoParticipacao
    @Override
    public void deleteTipoParticipacao(Long id){
        repository.deleteById(id);
    }

}
