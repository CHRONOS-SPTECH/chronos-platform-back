package chronos.tech.application.service;

import chronos.tech.application.dto.request.HistoricoAcademicoRequestDTO;
import chronos.tech.application.dto.response.HistoricoAcademicoResponseDTO;
import chronos.tech.application.mapper.HistoricoAcademicoMapper;
import chronos.tech.application.port.in.HistoricoAcademicoUseCase;
import chronos.tech.domain.model.classes.HistoricoAcademico;
import chronos.tech.domain.port.HistoricoAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoAcademicoService implements HistoricoAcademicoUseCase {
    private final HistoricoAcademicoRepository repository;
    private final HistoricoAcademicoMapper mapper;

    @Override
    public List<HistoricoAcademicoResponseDTO> getAllHistoricos() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public HistoricoAcademicoResponseDTO getHistorico(Long id) {
        HistoricoAcademico historico = repository.findById(id).orElseThrow(() -> new RuntimeException("Historico nao encontrado: " + id));
        return mapper.toResponse(historico);
    }

    @Override
    public HistoricoAcademicoResponseDTO saveHistorico(HistoricoAcademicoRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
    }

    @Override
    public HistoricoAcademicoResponseDTO updateHistorico(Long id, HistoricoAcademicoRequestDTO dto) {
        HistoricoAcademico existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Historico nao encontrado: " + id));
        mapper.updateFromDto(dto, existente);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deleteHistorico(Long id) {
        repository.deleteById(id);
    }
}
