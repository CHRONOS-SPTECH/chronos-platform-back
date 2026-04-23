package chronos.tech.application.service;

import chronos.tech.application.dto.request.GestaoSecretariaRequestDTO;
import chronos.tech.application.dto.response.GestaoSecretariaResponseDTO;
import chronos.tech.application.mapper.GestaoSecretariaMapper;
import chronos.tech.application.port.in.GestaoSecretariaUseCase;
import chronos.tech.domain.model.classes.GestaoSecretaria;
import chronos.tech.domain.port.GestaoSecretariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestaoSecretariaService implements GestaoSecretariaUseCase {
    private final GestaoSecretariaRepository repository;
    private final GestaoSecretariaMapper mapper;

    @Override
    public List<GestaoSecretariaResponseDTO> getAllGestoes() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public GestaoSecretariaResponseDTO getGestao(Long id) {
        GestaoSecretaria gestao = repository.findById(id).orElseThrow(() -> new RuntimeException("Gestao nao encontrada: " + id));
        return mapper.toResponse(gestao);
    }

    @Override
    public GestaoSecretariaResponseDTO saveGestao(GestaoSecretariaRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
    }

    @Override
    public GestaoSecretariaResponseDTO updateGestao(Long id, GestaoSecretariaRequestDTO dto) {
        GestaoSecretaria existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Gestao nao encontrada: " + id));
        mapper.updateFromDto(dto, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deleteGestao(Long id) {
        repository.deleteById(id);
    }
}
