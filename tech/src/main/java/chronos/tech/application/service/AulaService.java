package chronos.tech.application.service;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaResponseDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.mapper.AulaMapper;
import chronos.tech.application.mapper.MateriaMapper;
import chronos.tech.application.mapper.TemaAulaMapper;
import chronos.tech.application.port.in.AulaUseCase;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService implements AulaUseCase {
    private final AulaRepository repository;
    private final AulaMapper mapper;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;
    private final TemaAulaMapper temaMapper;
    private final MateriaMapper materiaMapper;

    public List<AulaResponseDTO> getAllAulas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public AulaResponseDTO getAula(Integer id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id)));
    }

    public AulaResponseDTO saveAula(AulaRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
    }

    public AulaResponseDTO updateAula(Integer id, AulaRequestDTO dto) {
        Aula a = repository.findById(id).orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id));
        mapper.updateFromDto(dto, a);
        return mapper.toResponse(repository.save(a));
    }

    public void deleteAula(Integer id) {
        repository.deleteById(id);
    }

    public List<AulaComTemaEMateriaResponseDTO> getAulasDoDia(Date data, Integer instrutorId) {
        List<Aula> aulasDoDia = repository.findByDataAulaAndInstrutorIdPessoa(data, instrutorId);

        return aulasDoDia.stream()
                .map(a -> {
                    Boolean chamadaFeita = chamadaAulaRepository.existsByAula(a);

                    return new AulaComTemaEMateriaResponseDTO(
                            mapper.toResponse(a),
                            temaMapper.toResponse(a.getTema()),
                            materiaMapper.toResponse(a.getTema().getIdMateria()),
                            chamadaFeita
                    );
                })
                .toList();
    }

    public AulaComTemaEMateriaResponseDTO getAulaComTemaEMateriaPorId(Integer id) {
        Aula aula = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada com o ID: " + id));

        Boolean chamadaFeita = chamadaAulaRepository.existsByAula(aula);

        return new AulaComTemaEMateriaResponseDTO(
                mapper.toResponse(aula),
                temaMapper.toResponse(aula.getTema()),
                materiaMapper.toResponse(aula.getTema().getIdMateria()),
                chamadaFeita
        );
    }
}