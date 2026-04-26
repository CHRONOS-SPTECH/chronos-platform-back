package chronos.tech.application.service;

import chronos.tech.application.dto.request.UsuarioPerfilRequestDTO;
import chronos.tech.application.dto.response.UsuarioPerfilResponseDTO;
import chronos.tech.application.mapper.UsuarioPerfilMapper;
import chronos.tech.application.port.in.UsuarioPerfilUseCase;
import chronos.tech.domain.model.classes.UsuarioPerfil;
import chronos.tech.domain.port.UsuarioPerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioPerfilService implements UsuarioPerfilUseCase {
    private final UsuarioPerfilRepository repository;
    private final UsuarioPerfilMapper mapper;

    public List<UsuarioPerfilResponseDTO> getAllVinculos() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
    public UsuarioPerfilResponseDTO getVinculo(Long id) { return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Vinculo nao encontrado: " + id))); }
    public UsuarioPerfilResponseDTO saveVinculo(UsuarioPerfilRequestDTO dto) { return mapper.toResponse(repository.save(mapper.toModel(dto))); }
    public UsuarioPerfilResponseDTO updateVinculo(Long id, UsuarioPerfilRequestDTO dto) { UsuarioPerfil v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vinculo nao encontrado: " + id)); mapper.updateFromDto(dto, v); return mapper.toResponse(repository.save(v)); }
    public void deleteVinculo(Long id) { repository.deleteById(id); }
}
