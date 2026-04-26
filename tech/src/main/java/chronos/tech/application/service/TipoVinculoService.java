package chronos.tech.application.service;

import chronos.tech.application.dto.request.TipoVinculoRequestDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;
import chronos.tech.application.mapper.TipoVinculoMapper;
import chronos.tech.application.port.in.TipoVinculoUseCase;
import chronos.tech.domain.model.classes.TipoVinculo;
import chronos.tech.domain.port.TipoVinculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoVinculoService implements TipoVinculoUseCase {
    private final TipoVinculoRepository repository;
    private final TipoVinculoMapper mapper;

    public List<TipoVinculoResponseDTO> getAllTiposVinculo() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
    public TipoVinculoResponseDTO getTipoVinculo(Integer id) { return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("TipoVinculo nao encontrado: " + id))); }
    public TipoVinculoResponseDTO saveTipoVinculo(TipoVinculoRequestDTO dto) { return mapper.toResponse(repository.save(mapper.toModel(dto))); }
    public TipoVinculoResponseDTO updateTipoVinculo(Integer id, TipoVinculoRequestDTO dto) { TipoVinculo t = repository.findById(id).orElseThrow(() -> new RuntimeException("TipoVinculo nao encontrado: " + id)); mapper.updateFromDto(dto, t); return mapper.toResponse(repository.save(t)); }
    public void deleteTipoVinculo(Integer id) { repository.deleteById(id); }
}
