package chronos.tech.application.service;

import chronos.tech.application.dto.request.MateriaRequestDTO;
import chronos.tech.application.dto.response.MateriaResponseDTO;
import chronos.tech.application.mapper.MateriaMapper;
import chronos.tech.application.port.in.MateriaUseCase;
import chronos.tech.domain.model.classes.Materia;
import chronos.tech.domain.port.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaService implements MateriaUseCase {
    private final MateriaRepository repository;
    private final MateriaMapper mapper;

    @Override
    public List<MateriaResponseDTO> getAllMaterias() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public MateriaResponseDTO getMateria(Long id) {
        Materia materia = repository.findById(id).orElseThrow(() -> new RuntimeException("Materia nao encontrada: " + id));
        return mapper.toResponse(materia);
    }

    @Override
    public MateriaResponseDTO saveMateria(MateriaRequestDTO materia) {
        return mapper.toResponse(repository.save(mapper.toModel(materia)));
    }

    @Override
    public MateriaResponseDTO updateMateria(Long id, MateriaRequestDTO materia) {
        Materia existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Materia nao encontrada: " + id));
        mapper.updateFromDto(materia, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deleteMateria(Long id) {
        repository.deleteById(id);
    }
}
