package chronos.tech.application.service;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.application.mapper.TurmaMapper;
import chronos.tech.application.port.in.TurmaUseCase;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurmaService implements TurmaUseCase {

    private final TurmaRepository repository;
    private final TurmaMapper mapper;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;

    //Método para pegar todas as turmas
    @Override
    public List<TurmaResponseDTO> getAllTurmas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    //Método pegar uma turma específica
    @Override
    public TurmaResponseDTO getTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrado com o ID: " + id));
        return mapper.toResponse(turma);
    }

    //Salvar no banco de dados h2
    @Override
    public TurmaResponseDTO saveTurma(TurmaRequestDTO turma) {
        Turma entidade = mapper.toModel(turma);
        return mapper.toResponse(repository.save(entidade));
    }

    //Atualizar a turma
    @Override
    public TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado) {
        Turma turmaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrado com o ID: " + id));

        mapper.updateFromDto(turmaAtualizado, turmaExistente);
        return mapper.toResponse(repository.save(turmaExistente));
    }

    //Deletar uma turma
    @Override
    public void deleteTurma(Long id) {
        repository.deleteById(id);
    }

    //Listar todos os alunos de uma turma com presença
    @Override
    public List<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId) {
        List<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaIdTurma(turmaId);

        return matriculas.stream()
                .map(m -> {
                    Integer pessoaId = m.getPessoa().getIdPessoa();

                    List<ChamadaAula> chamadasAluno = chamadaAulaRepository
                            .findByPessoaIdPessoaAndAulaTurmaIdTurma(pessoaId, turmaId);

                    double percentualPresenca = 0.0;
                    if (!chamadasAluno.isEmpty()) {
                        long compareceuCount = chamadasAluno.stream()
                                .filter(c -> c.getCompareceu() != null && c.getCompareceu())
                                .count();
                        percentualPresenca = (compareceuCount * 100.0) / chamadasAluno.size();
                    }

                    return new AlunoComPresencaResponseDTO(
                            pessoaId,
                            m.getPessoa().getNome(),
                            m.getPessoa().getCpf(),
                            m.getPessoa().getGenero(),
                            m.getPessoa().getEmail(),
                            percentualPresenca
                    );
                })
                .collect(Collectors.toList());
    }

}
