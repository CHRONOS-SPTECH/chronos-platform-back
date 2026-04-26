package chronos.tech.application.service;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.mapper.AulaMapper;
import chronos.tech.application.port.in.AulaUseCase;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.port.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService implements AulaUseCase {
    private final AulaRepository repository;
    private final AulaMapper mapper;

    public List<AulaResponseDTO> getAllAulas() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
    public AulaResponseDTO getAula(Integer id) { return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id))); }
    public AulaResponseDTO saveAula(AulaRequestDTO dto) { return mapper.toResponse(repository.save(mapper.toModel(dto))); }
    public AulaResponseDTO updateAula(Integer id, AulaRequestDTO dto) { Aula a = repository.findById(id).orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id)); mapper.updateFromDto(dto, a); return mapper.toResponse(repository.save(a)); }
    public void deleteAula(Integer id) { repository.deleteById(id); }
}
