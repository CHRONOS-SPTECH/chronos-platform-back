package chronos.tech.application.service;

import chronos.tech.application.dto.request.TemaAulaRequestDTO;
import chronos.tech.application.dto.response.TemaAulaResponseDTO;
import chronos.tech.application.mapper.TemaAulaMapper;
import chronos.tech.application.port.in.TemaAulaUseCase;
import chronos.tech.domain.model.classes.TemaAula;
import chronos.tech.domain.port.TemaAulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemaAulaService implements TemaAulaUseCase {
    private final TemaAulaRepository repository;
    private final TemaAulaMapper mapper;

    @Override
    public List<TemaAulaResponseDTO> getAllTemas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public TemaAulaResponseDTO getTema(Long id) {
        TemaAula tema = repository.findById(id).orElseThrow(() -> new RuntimeException("Tema nao encontrado: " + id));
        return mapper.toResponse(tema);
    }

    @Override
    public TemaAulaResponseDTO saveTema(TemaAulaRequestDTO tema) {
        return mapper.toResponse(repository.save(mapper.toModel(tema)));
    }

    @Override
    public TemaAulaResponseDTO updateTema(Long id, TemaAulaRequestDTO tema) {
        TemaAula existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Tema nao encontrado: " + id));
        mapper.updateFromDto(tema, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deleteTema(Long id) {
        repository.deleteById(id);
    }
}
