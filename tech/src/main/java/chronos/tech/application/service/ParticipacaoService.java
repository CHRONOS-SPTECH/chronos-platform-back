package chronos.tech.application.service;

import chronos.tech.application.dto.request.ParticipacaoRequestDTO;
import chronos.tech.application.dto.response.ParticipacaoResponseDTO;
import chronos.tech.application.mapper.ParticipacaoMapper;
import chronos.tech.application.port.in.ParticipacaoUseCase;
import chronos.tech.domain.model.classes.Participacao;
import chronos.tech.domain.port.ParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipacaoService implements ParticipacaoUseCase {
    private final ParticipacaoRepository repository;
    private final ParticipacaoMapper mapper;

    @Override
    public List<ParticipacaoResponseDTO> getAllParticipacoes() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ParticipacaoResponseDTO getParticipacao(Long id) {
        Participacao participacao = repository.findById(id).orElseThrow(() -> new RuntimeException("Participacao nao encontrada: " + id));
        return mapper.toResponse(participacao);
    }

    @Override
    public ParticipacaoResponseDTO saveParticipacao(ParticipacaoRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
    }

    @Override
    public ParticipacaoResponseDTO updateParticipacao(Long id, ParticipacaoRequestDTO dto) {
        Participacao existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Participacao nao encontrada: " + id));
        mapper.updateFromDto(dto, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deleteParticipacao(Long id) {
        repository.deleteById(id);
    }
}
