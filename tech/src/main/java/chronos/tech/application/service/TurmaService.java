package chronos.tech.application.service;

import chronos.tech.application.dto.turma.TurmaRequestDTO;
import chronos.tech.application.dto.turma.AvaliacaoFormacaoResponseDTO;
import chronos.tech.application.dto.turma.DecisaoFormacaoRequestDTO;
import chronos.tech.application.dto.turma.AlunoDisponivelMatriculaResponseDTO;
import chronos.tech.application.dto.turma.TurmaDetalhesResponseDTO;
import chronos.tech.application.dto.pessoa.AlunoComPresencaResponseDTO;
import chronos.tech.application.dto.turma.TurmaAlunoPaginaResponseDTO;
import chronos.tech.application.dto.turma.TurmaAlunoResponseDTO;
import chronos.tech.application.dto.turma.TurmaDeletadaResponseDTO;
import chronos.tech.application.dto.turma.TurmaResponseDTO;
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
import java.time.ZoneId;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TurmaService implements TurmaUseCase {
    private static final ZoneId APP_ZONE = ZoneId.of("America/Sao_Paulo");

    private final TurmaRepository repository;
    private final TurmaMapper mapper;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;
    private final FormacaoTurmaService formacaoTurmaService;
    private final TurmaDetalhesService turmaDetalhesService;

    @Override
    public List<TurmaResponseDTO> getAllTurmas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TurmaResponseDTO> getAllTurmas(Pageable pageable) {
        return repository.findAll(limitarPagina(pageable)).map(mapper::toResponse);
    }

    @Override
    public TurmaResponseDTO getTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + id));
        return mapper.toResponse(turma);
    }

    @Override
    public TurmaResponseDTO saveTurma(TurmaRequestDTO turma) {
        validarDatas(turma.data_inicio(), turma.data_encerramento());
        if (turma.status_turma() == StatusTurma.CONCLUIDA || turma.status_turma() == StatusTurma.INATIVA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Turma deve ser encerrada ou inativada pelo fluxo correspondente");
        }
        if (turma.status_turma() == StatusTurma.EM_ANDAMENTO
                && turma.data_inicio() != null && turma.data_inicio().isAfter(LocalDate.now(APP_ZONE))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Turma futura não pode ser criada como em andamento");
        }
        Turma entidade = mapper.toModel(turma);
        if (entidade.getStatusTurma() == null) entidade.setStatusTurma(StatusTurma.NAO_INICIADA);
        return mapper.toResponse(repository.save(entidade));
    }

    @Override
    public TurmaResponseDTO updateTurma(Long id, TurmaRequestDTO turmaAtualizado) {
        Turma turmaExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + id));

        validarDatas(
                turmaAtualizado.data_inicio() == null ? turmaExistente.getDataInicio() : turmaAtualizado.data_inicio(),
                turmaAtualizado.data_encerramento() == null ? turmaExistente.getDataEncerramento() : turmaAtualizado.data_encerramento());
        if (turmaAtualizado.status_turma() != null && turmaAtualizado.status_turma() != turmaExistente.getStatusTurma()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Altere o status pelos fluxos de inativação ou encerramento");
        }

        mapper.updateFromDto(turmaAtualizado, turmaExistente);
        return mapper.toResponse(repository.save(turmaExistente));
    }

    @Override
    public TurmaDeletadaResponseDTO deleteTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + id));

        if (turma.getStatusTurma() == StatusTurma.CONCLUIDA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Uma turma concluída não pode ser inativada");
        }

        turma.setStatusTurma(StatusTurma.INATIVA);
        repository.save(turma);

        return new TurmaDeletadaResponseDTO(
                "Turma desativada com sucesso",
                turma.getIdTurma().longValue(),
                turma.getStatusTurma()
        );
    }

    @Override
    public TurmaDeletadaResponseDTO iniciarTurma(Long id) {
        Turma turma = buscarTurma(id);
        if (turma.getStatusTurma() != StatusTurma.NAO_INICIADA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Somente turma não iniciada pode ser iniciada");
        }
        if (turma.getDataInicio() != null && turma.getDataInicio().isAfter(LocalDate.now(APP_ZONE))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A turma ainda não atingiu a data de início");
        }
        turma.setStatusTurma(StatusTurma.EM_ANDAMENTO);
        repository.save(turma);
        return new TurmaDeletadaResponseDTO("Turma iniciada com sucesso", id, turma.getStatusTurma());
    }

    @Override
    @Transactional
    public TurmaDeletadaResponseDTO encerrarTurma(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + id));

        if (turma.getStatusTurma() == StatusTurma.INATIVA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Uma turma inativa não pode ser encerrada");
        }
        if (turma.getStatusTurma() == StatusTurma.NAO_INICIADA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Uma turma ainda não iniciada não pode ser encerrada");
        }

        turma.setStatusTurma(StatusTurma.CONCLUIDA);

        // Se ainda não chegou na data de encerramento, força para hoje
        if (turma.getDataEncerramento() == null || LocalDate.now(APP_ZONE).isBefore(turma.getDataEncerramento())) {
            turma.setDataEncerramento(LocalDate.now(APP_ZONE));
        }

        repository.save(turma);

        formacaoTurmaService.criarPendencias(turma);

        return new TurmaDeletadaResponseDTO(
                "Turma encerrada com sucesso",
                turma.getIdTurma().longValue(),
                turma.getStatusTurma()
        );
    }

    @Transactional(readOnly = true)
    public Page<AvaliacaoFormacaoResponseDTO> listarFormandos(Long idTurma, Pageable pageable) {
        buscarTurma(idTurma);
        return formacaoTurmaService.listar(idTurma, pageable);
    }

    @Transactional
    public AvaliacaoFormacaoResponseDTO decidirFormacao(Long idTurma, Long idPessoa, DecisaoFormacaoRequestDTO decisao) {
        return formacaoTurmaService.decidir(idTurma, idPessoa, decisao);
    }

    private Turma buscarTurma(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + id));
    }

    private Pageable limitarPagina(Pageable pageable) {
        int tamanho = Math.min(pageable.getPageSize(), 100);
        return org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), tamanho, pageable.getSort());
    }

    private void validarDatas(LocalDate inicio, LocalDate encerramento) {
        if (inicio != null && encerramento != null && encerramento.isBefore(inicio)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de encerramento não pode ser anterior ao início");
        }
    }

    @Override
    public TurmaAlunoPaginaResponseDTO getAlunosByTurma(Long id, Pageable pageable) {
        Turma turma = buscarTurma(id);
        Page<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaIdTurma(
                turma.getIdTurma(), limitarPagina(pageable));
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

    @Override
    public Page<AlunoComPresencaResponseDTO> getAlunosDaTurmaComPresenca(Integer turmaId, Pageable pageable) {
        if (repository.findById(turmaId.longValue()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + turmaId);
        }
        Page<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaIdTurma(turmaId, limitarPagina(pageable));
        List<Integer> pessoaIds = matriculas.getContent().stream().map(m -> m.getPessoa().getIdPessoa()).toList();
        java.util.Map<Integer, List<ChamadaAula>> chamadasPorPessoa = chamadaAulaRepository
                .findByPessoaIdPessoaInAndAulaTurmaIdTurma(pessoaIds, turmaId).stream()
                .collect(java.util.stream.Collectors.groupingBy(c -> c.getPessoa().getIdPessoa()));
        return matriculas.map(matricula -> {
            Integer pessoaId = matricula.getPessoa().getIdPessoa();
            List<ChamadaAula> chamadasAluno = chamadasPorPessoa.getOrDefault(pessoaId, List.of());
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

    @Override
    @Transactional(readOnly = true)
    public Page<AlunoDisponivelMatriculaResponseDTO> getAlunosDisponiveis(Long turmaId, Pageable pageable) {
        Turma turma = buscarTurma(turmaId);
        if (turma.getStatusTurma() == StatusTurma.CONCLUIDA || turma.getStatusTurma() == StatusTurma.INATIVA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Turmas concluídas ou inativas não aceitam novas matrículas");
        }
        return matriculaTurmaRepository.findAlunosDisponiveis(turma.getIdTurma(), limitarPagina(pageable))
                .map(pessoa -> new AlunoDisponivelMatriculaResponseDTO(
                        pessoa.getIdPessoa().longValue(), pessoa.getNome(), pessoa.getEmail(),
                        pessoa.getTelefone(), pessoa.getTipoVinculo().getNome_vinculo()));
    }

    @Override
    public TurmaDetalhesResponseDTO getDetalhes(Long turmaId) {
        return turmaDetalhesService.obter(turmaId);
    }

}
