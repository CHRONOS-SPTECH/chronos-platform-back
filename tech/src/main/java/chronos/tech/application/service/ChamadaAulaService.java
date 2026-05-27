package chronos.tech.application.service;

import chronos.tech.application.dto.request.ChamadaAulaRequestDTO;
import chronos.tech.application.dto.request.ListaChamadaAulaRequestDTO;
import chronos.tech.application.dto.response.ChamadaAulaResponseDTO;
import chronos.tech.application.mapper.ChamadaAulaMapper;
import chronos.tech.application.port.in.ChamadaAulaUseCase;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusAula;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadaAulaService implements ChamadaAulaUseCase {
    private final ChamadaAulaRepository repository;
    private final ChamadaAulaMapper mapper;
    private final AulaRepository aulaRepository;

    public List<ChamadaAulaResponseDTO> getAllChamadas() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
    public ChamadaAulaResponseDTO getChamada(Long id) { return mapper.toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Chamada nao encontrada: " + id))); }
    public ChamadaAulaResponseDTO saveChamada(ChamadaAulaRequestDTO dto) { return mapper.toResponse(repository.save(mapper.toModel(dto))); }
    public ChamadaAulaResponseDTO updateChamada(Long id, ChamadaAulaRequestDTO dto) { ChamadaAula c = repository.findById(id).orElseThrow(() -> new RuntimeException("Chamada nao encontrada: " + id)); mapper.updateFromDto(dto, c); return mapper.toResponse(repository.save(c)); }
    public void deleteChamada(Long id) { repository.deleteById(id); }

    @Override
    @Transactional
    public List<ChamadaAulaResponseDTO> saveListaChamada(ListaChamadaAulaRequestDTO dto) {

        List<ChamadaAula> chamadas = dto.alunos().stream().map(aluno -> {
            ChamadaAulaRequestDTO individualDto = new ChamadaAulaRequestDTO(
                    Long.valueOf(dto.id_aula()),
                    aluno.id_pessoa(),
                    aluno.compareceu()
            );
            return mapper.toModel(individualDto);
        }).toList();

        // Salva todos de uma vez no banco
        List<ChamadaAula> chamadasSalvas = repository.saveAll(chamadas);

        // Transforma o resultado no DTO de resposta
        return chamadasSalvas.stream()
                .map(mapper::toResponse)
                .toList();
    }
}
