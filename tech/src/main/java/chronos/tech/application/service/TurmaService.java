package chronos.tech.application.service;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.response.TurmaAlunoPaginaResponseDTO;
import chronos.tech.application.dto.response.TurmaAlunoResponseDTO;
import chronos.tech.application.dto.response.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.application.mapper.TurmaMapper;
import chronos.tech.application.port.in.TurmaUseCase;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public TurmaDeletadaResponseDTO deleteTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + id));

        turma.setStatusTurma(StatusTurma.INATIVA);
        repository.save(turma);

        return new TurmaDeletadaResponseDTO(
                "Turma desativada com sucesso",
                turma.getIdTurma().longValue(),
                turma.getStatusTurma()
        );
    }

    @Override
    public TurmaDeletadaResponseDTO encerrarTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + id));

        turma.setStatusTurma(StatusTurma.CONCLUIDA);

        // Se ainda não chegou na data de encerramento, força para hoje
        if (LocalDate.now().isBefore(turma.getDataEncerramento())) {
            turma.setDataEncerramento(LocalDate.now());
        }

        repository.save(turma);

        return new TurmaDeletadaResponseDTO(
                "Turma encerrada com sucesso",
                turma.getIdTurma().longValue(),
                turma.getStatusTurma()
        );
    }

    @Override
    public TurmaAlunoResponseDTO getAlunosByTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + id));

        List<TurmaAlunoResponseDTO.AlunoResumoDTO> alunos = turma.getMatriculaTurmas()
                .stream()
                .map(matricula -> {
                    Pessoa p = matricula.getPessoa();
                    return new TurmaAlunoResponseDTO.AlunoResumoDTO(
                            p.getIdPessoa().longValue(),
                            p.getNome(),
                            p.getEmail(),
                            p.getDataSaida() == null ? "Ativo" : "Inativo"
                    );
                })
                .toList();

        return new TurmaAlunoResponseDTO(
                turma.getIdTurma().longValue(),
                turma.getNomeTurma(),
                alunos.size(),
                alunos
        );
    }

    @Override
    public TurmaAlunoPaginaResponseDTO getAlunosByTurma(Long id, Pageable pageable) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + id));
        Page<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaIdTurma(
                turma.getIdTurma(), pageable);
        List<TurmaAlunoResponseDTO.AlunoResumoDTO> alunos = matriculas.map(matricula -> {
            Pessoa pessoa = matricula.getPessoa();
            return new TurmaAlunoResponseDTO.AlunoResumoDTO(
                    pessoa.getIdPessoa().longValue(), pessoa.getNome(), pessoa.getEmail(),
                    pessoa.getDataSaida() == null ? "Ativo" : "Inativo");
        }).getContent();
        return new TurmaAlunoPaginaResponseDTO(
                turma.getIdTurma().longValue(), turma.getNomeTurma(), matriculas.getTotalElements(),
                matriculas.getNumber(), matriculas.getSize(), matriculas.getTotalPages(), alunos);
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

    @Override
    public Page<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId, Pageable pageable) {
        return matriculaTurmaRepository.findByTurmaIdTurma(turmaId, pageable).map(matricula -> {
            Integer pessoaId = matricula.getPessoa().getIdPessoa();
            List<ChamadaAula> chamadasAluno = chamadaAulaRepository
                    .findByPessoaIdPessoaAndAulaTurmaIdTurma(pessoaId, turmaId);
            double percentualPresenca = 0.0;
            if (!chamadasAluno.isEmpty()) {
                long compareceuCount = chamadasAluno.stream()
                        .filter(chamada -> Boolean.TRUE.equals(chamada.getCompareceu()))
                        .count();
                percentualPresenca = compareceuCount * 100.0 / chamadasAluno.size();
            }
            Pessoa pessoa = matricula.getPessoa();
            return new AlunoComPresencaResponseDTO(pessoaId, pessoa.getNome(), pessoa.getCpf(),
                    pessoa.getGenero(), pessoa.getEmail(), percentualPresenca);
        });
    }

}
