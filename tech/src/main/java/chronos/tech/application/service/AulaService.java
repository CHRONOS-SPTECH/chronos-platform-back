package chronos.tech.application.service;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.request.LinhaPlanilhaDTO;
import chronos.tech.application.dto.request.MovimentacaoAulaDTO;
import chronos.tech.application.dto.response.*;
import chronos.tech.application.mapper.*;
import chronos.tech.application.port.in.AulaUseCase;
import chronos.tech.application.util.ExcelProcessor;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TemaAula;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusAula;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.domain.port.TemaAulaRepository;
import chronos.tech.domain.port.TurmaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AulaService implements AulaUseCase {

    private final AulaRepository repository;
    private final AulaMapper mapper;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;
    private final TemaAulaMapper temaMapper;
    private final MateriaMapper materiaMapper;
    private final TurmaRepository turmaRepository;
    private final PessoaRepository pessoaRepository;
    private final TemaAulaRepository temaAulaRepository;
    private final PessoaMapper pessoaMapper;
    private final TurmaMapper turmaMapper;

    public List<AulaResponseDTO> getAllAulas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public AulaResponseDTO getAula(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id)));
    }

    public AulaResponseDTO saveAula(AulaRequestDTO dto) {
        Aula aula = mapper.toModel(dto);

        aula.setStatusAula(StatusAula.AGENDADA);
        aula.setData_criacao_registro(LocalDateTime.now());

        return mapper.toResponse(repository.save(aula));
    }

    public AulaResponseDTO updateAula(Integer id, AulaRequestDTO dto) {
        Aula a = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id));
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

    public List<AulaComTemaEMateriaComInstrutorResponseDTO> getAllAulasDetails() {
        return repository.findAll().stream()
                .map(this::converterParaDtoCompleto)
                .toList();
    }

    public List<AulaComTemaEMateriaComInstrutorResponseDTO> getAulasPorTurma(Integer idTurma) {
        return repository.findByTurmaIdTurma(idTurma).stream()
                .map(this::converterParaDtoCompleto)
                .toList();
    }

    @Override
    @Transactional
    public RelatorioImportacaoResponseDTO importarCronograma(MultipartFile file) {
        List<ItemRelatorioImportacaoResponseDTO> falhas = new ArrayList<>();
        int totalSucesso = 0;
        List<LinhaPlanilhaDTO> linhasBrutas;

        try {
            linhasBrutas = ExcelProcessor.extrairDados(file);
        } catch (Exception e) {
            throw new RuntimeException("Falha crítica ao ler o arquivo Excel: " + e.getMessage());
        }

        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm");

        for (LinhaPlanilhaDTO linha : linhasBrutas) {
            try {
                LocalDate localData;
                LocalTime localHoraInicio;
                LocalTime localHoraFim;

                try {
                    localData = LocalDate.parse(linha.data(), formatadorData);
                    localHoraInicio = LocalTime.parse(linha.horario().trim(), formatadorHora);
                    localHoraFim = localHoraInicio.plusHours(1).plusMinutes(30);
                } catch (Exception e) {
                    throw new RuntimeException("Formato de data ou hora inválido no Excel. Use 'dd/MM/yyyy' e 'HH:mm'");
                }

                Date dataFormatada = Date.valueOf(localData);
                Time timeInicio = Time.valueOf(localHoraInicio);
                Time timeFim = Time.valueOf(localHoraFim);

                Turma turma = turmaRepository.findByNomeTurma(linha.turma())
                        .orElseThrow(() -> new RuntimeException("Turma não encontrada no sistema: '" + linha.turma() + "'"));

                Pessoa professor = pessoaRepository.findByNome(linha.professor())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado no sistema: '" + Objects.toString(linha.professor(), "N/A") + "'"));

                TemaAula tema = temaAulaRepository.findByTituloTema(linha.materia())
                        .orElseThrow(() -> new RuntimeException("Tema não encontrado: " + linha.materia()));

                // Buscar os registros existentes para o dia
                List<Aula> aulasDaTurmaNoDia = repository.findByTurmaIdTurmaAndDataAula(turma.getIdTurma(), dataFormatada);
                List<Aula> aulasDoProfessorNoDia = repository.findByInstrutorIdPessoaAndDataAula(professor.getIdPessoa(), dataFormatada);

                // --- REGRA 1: Se já existe um registro EXATO, ignora e pula.
                boolean existeRegistroIdentico = aulasDaTurmaNoDia.stream()
                        .anyMatch(a ->
                                a.getHoraInicio() != null && a.getHoraFim() != null
                                        && a.getHoraInicio().equals(timeInicio)
                                        && a.getHoraFim().equals(timeFim)
                                        && a.getInstrutor() != null && a.getInstrutor().getIdPessoa().equals(professor.getIdPessoa())
                                        && a.getTema() != null && a.getTema().getIdTema().equals(tema.getIdTema())
                        );

                if (existeRegistroIdentico) {
                    totalSucesso++;
                    continue;
                }

                // --- REGRA 2: Se já existe aula na mesma DATA/TURMA ou conflito de horário, salva como PENDENTE (sem data e hora)
                boolean existeAulaDiferenteNoMesmoDia = !aulasDaTurmaNoDia.isEmpty();

                boolean conflitoHorarioProfessor = aulasDoProfessorNoDia.stream()
                        .filter(a -> a.getHoraInicio() != null && a.getHoraFim() != null)
                        .anyMatch(a -> timeInicio.before(a.getHoraFim()) && timeFim.after(a.getHoraInicio()));

                boolean conflitoHorarioTurma = aulasDaTurmaNoDia.stream()
                        .filter(a -> a.getHoraInicio() != null && a.getHoraFim() != null)
                        .anyMatch(a -> timeInicio.before(a.getHoraFim()) && timeFim.after(a.getHoraInicio()));

                if (existeAulaDiferenteNoMesmoDia || conflitoHorarioProfessor || conflitoHorarioTurma) {
                    Aula novaPendente = new Aula();
                    novaPendente.setTurma(turma);
                    novaPendente.setInstrutor(professor);
                    novaPendente.setTema(tema);
                    novaPendente.setDataAula(null);
                    novaPendente.setHoraInicio(null);
                    novaPendente.setHoraFim(null);
                    novaPendente.setStatusAula(StatusAula.AGENDADA);
                    novaPendente.setData_criacao_registro(LocalDateTime.now());

                    repository.save(novaPendente);
                    totalSucesso++;
                    continue;
                }

                // --- FLUXO NORMAL
                Aula novaAula = new Aula();
                novaAula.setTurma(turma);
                novaAula.setInstrutor(professor);
                novaAula.setTema(tema);
                novaAula.setDataAula(dataFormatada);
                novaAula.setHoraInicio(timeInicio);
                novaAula.setHoraFim(timeFim);
                novaAula.setStatusAula(StatusAula.AGENDADA);
                novaAula.setData_criacao_registro(LocalDateTime.now());

                repository.save(novaAula);
                totalSucesso++;

            } catch (Exception e) {
                falhas.add(new ItemRelatorioImportacaoResponseDTO(
                        linha.numeroLinha(),
                        linha.turma(),
                        linha.professor(),
                        linha.materia(),
                        e.getMessage()
                ));
            }
        }

        return new RelatorioImportacaoResponseDTO(
                linhasBrutas.size(),
                totalSucesso,
                falhas.size(),
                falhas
        );
    }

    @Override
    @Transactional
    public void remanejarAulasEmLote(List<MovimentacaoAulaDTO> movimentacoes) {
        for (MovimentacaoAulaDTO mov : movimentacoes) {
            Aula aula = repository.findById(mov.idAula())
                    .orElseThrow(() -> new RuntimeException("Aula não encontrada no sistema com o ID: " + mov.idAula()));

            // Se a nova data ou hora vier nula, limpa o registro
            if (mov.dataAula() == null || mov.horaInicio() == null) {
                aula.setDataAula(null);
                aula.setHoraInicio(null);
                aula.setHoraFim(null);
            } else {
                try {
                    LocalDate localData = LocalDate.parse(mov.dataAula());
                    LocalTime localHoraInicio = LocalTime.parse(mov.horaInicio());
                    LocalTime localHoraFim = localHoraInicio.plusHours(2);

                    Date dataBanco = Date.valueOf(localData);
                    Time inicioBanco = Time.valueOf(localHoraInicio);
                    Time fimBanco = Time.valueOf(localHoraFim);

                    this.validarConflitosDeRemanejamento(aula, dataBanco, inicioBanco, fimBanco);

                    aula.setDataAula(dataBanco);
                    aula.setHoraInicio(inicioBanco);
                    aula.setHoraFim(fimBanco);

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao converter dados de tempo na aula ID " + mov.idAula() + ": " + e.getMessage());
                }
            }

            repository.save(aula);
        }
    }

    private void validarConflitosDeRemanejamento(Aula aula, Date data, Time inicio, Time fim) {
        // Validação do Instrutor
        if (aula.getInstrutor() != null) {
            boolean profOcupado = repository.findByInstrutorIdPessoaAndDataAulaAndIdAulaNot(aula.getInstrutor().getIdPessoa(), data, aula.getIdAula()).stream()
                    .anyMatch(a -> inicio.before(a.getHoraFim()) && fim.after(a.getHoraInicio()));
            if (profOcupado) {
                throw new RuntimeException("Conflito: O Prof. " + aula.getInstrutor().getNome() + " já tem aula agendada neste horário.");
            }
        }

        // Validação da Turma
        if (aula.getTurma() != null) {
            boolean turmaOcupada = repository.findByTurmaIdTurmaAndDataAulaAndIdAulaNot(aula.getTurma().getIdTurma(), data, aula.getIdAula()).stream()
                    .anyMatch(a -> inicio.before(a.getHoraFim()) && fim.after(a.getHoraInicio()));
            if (turmaOcupada) {
                throw new RuntimeException("Conflito: A turma '" + aula.getTurma().getNomeTurma() + "' já possui outra aula agendada neste horário.");
            }
        }
    }

    private AulaComTemaEMateriaComInstrutorResponseDTO converterParaDtoCompleto(Aula aula) {
        Boolean chamadaFeita = chamadaAulaRepository.existsByAula(aula);
        TurmaResponseDTO turma = turmaMapper.toResponse(aula.getTurma());

        return new AulaComTemaEMateriaComInstrutorResponseDTO(
                mapper.toResponse(aula),
                temaMapper.toResponse(aula.getTema()),
                materiaMapper.toResponse(aula.getTema() != null ? aula.getTema().getIdMateria() : null),
                pessoaMapper.toResumidoResponse(aula.getInstrutor()),
                turma,
                chamadaFeita
        );
    }
}