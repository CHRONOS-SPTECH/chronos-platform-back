package chronos.tech.application.service;

import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.application.mapper.ChamadaAulaMapper;
import chronos.tech.application.port.in.ChamadaAulaUseCase;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.port.ChamadaAulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadaAulaService implements ChamadaAulaUseCase {
    private final ChamadaAulaRepository repository;
    private final ChamadaAulaMapper mapper;

    public List<ChamadaAulaResponseDTO> getAllChamadas() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
    public ChamadaAulaResponseDTO getChamada(Long id) { return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Chamada nao encontrada: " + id))); }
    public ChamadaAulaResponseDTO saveChamada(ChamadaAulaRequestDTO dto) { return mapper.toResponse(repository.save(mapper.toModel(dto))); }
    public ChamadaAulaResponseDTO updateChamada(Long id, ChamadaAulaRequestDTO dto) { ChamadaAula c = repository.findById(id).orElseThrow(() -> new RuntimeException("Chamada nao encontrada: " + id)); mapper.updateFromDto(dto, c); return mapper.toResponse(repository.save(c)); }
    public void deleteChamada(Long id) { repository.deleteById(id); }
}
