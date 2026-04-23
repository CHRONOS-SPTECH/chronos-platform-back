package chronos.tech.application.service;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;
import chronos.tech.application.mapper.MatriculaTurmaMapper;
import chronos.tech.application.port.in.MatriculaTurmaUseCase;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaTurmaService implements MatriculaTurmaUseCase {
    private final MatriculaTurmaRepository repository;
    private final MatriculaTurmaMapper mapper;

    @Override
    public List<MatriculaTurmaResponseDTO> getAllMatriculas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public MatriculaTurmaResponseDTO getMatricula(Long id) {
        MatriculaTurma matricula = repository.findById(id).orElseThrow(() -> new RuntimeException("Matricula nao encontrada: " + id));
        return mapper.toResponse(matricula);
    }

    @Override
    public MatriculaTurmaResponseDTO saveMatricula(MatriculaTurmaRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
    }

    @Override
    public MatriculaTurmaResponseDTO updateMatricula(Long id, MatriculaTurmaRequestDTO dto) {
        MatriculaTurma existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Matricula nao encontrada: " + id));
        mapper.updateFromDto(dto, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deleteMatricula(Long id) {
        repository.deleteById(id);
    }
}
