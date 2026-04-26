package chronos.tech.application.service;

import chronos.tech.application.dto.request.EnderecoPessoaRequestDTO;
import chronos.tech.application.dto.response.EnderecoPessoaResponseDTO;
import chronos.tech.application.mapper.EnderecoPessoaMapper;
import chronos.tech.application.port.in.EnderecoPessoaUseCase;
import chronos.tech.domain.model.classes.EnderecoPessoa;
import chronos.tech.domain.port.EnderecoPessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoPessoaService implements EnderecoPessoaUseCase {
    private final EnderecoPessoaRepository repository;
    private final EnderecoPessoaMapper mapper;

    public List<EnderecoPessoaResponseDTO> getAllEnderecos() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
    public EnderecoPessoaResponseDTO getEndereco(Integer id) { return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Endereco nao encontrado: " + id))); }
    public EnderecoPessoaResponseDTO saveEndereco(EnderecoPessoaRequestDTO dto) { return mapper.toResponse(repository.save(mapper.toModel(dto))); }
    public EnderecoPessoaResponseDTO updateEndereco(Integer id, EnderecoPessoaRequestDTO dto) { EnderecoPessoa e = repository.findById(id).orElseThrow(() -> new RuntimeException("Endereco nao encontrado: " + id)); mapper.updateFromDto(dto, e); return mapper.toResponse(repository.save(e)); }
    public void deleteEndereco(Integer id) { repository.deleteById(id); }
}
