package chronos.tech.application.service;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.application.mapper.TurmaMapper;
import chronos.tech.application.port.in.TurmaUseCase;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService implements TurmaUseCase {

    private final TurmaRepository repository;
    private final TurmaMapper mapper;

    //Método para pegar todas as turmas
    @Override
    public List<TurmaResponseDTO> getAllTurmas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar uma turma específica
    @Override
    public TurmaResponseDTO getTurma(Long id){
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrado com o ID: " + id));
        return mapper.toResponse(turma);
    }

    //Salvar no banco de dados h2
    @Override
    public TurmaResponseDTO saveTurma(TurmaRequestDTO turma){
        Turma entidade = mapper.toModel(turma);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar a turma
    @Override
    public TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado){
        Turma turmaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrado com o ID: " + id));

        mapper.updateFromDto(turmaAtualizado, turmaExistente);
        return mapper.toResponse(repository.save(turmaExistente));
    }

    //Deletar uma turma
    @Override
    public void deleteTurma(Long id){
        repository.deleteById(id);
    }

}
