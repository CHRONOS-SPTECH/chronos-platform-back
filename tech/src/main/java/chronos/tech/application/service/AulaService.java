package chronos.tech.application.service;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.request.LinhaPlanilhaDTO;
import chronos.tech.application.dto.response.AulaComTemaEMateriaResponseDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.dto.response.ItemRelatorioImportacaoResponseDTO;
import chronos.tech.application.dto.response.RelatorioImportacaoResponseDTO;
import chronos.tech.application.mapper.AulaMapper;
import chronos.tech.application.mapper.MateriaMapper;
import chronos.tech.application.mapper.TemaAulaMapper;
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
    private final ChamadaAulaRepository chamadaAulaRepository;
    private final TemaAulaMapper temaMapper;
    private final MateriaMapper materiaMapper;
    private final TurmaRepository turmaRepository;
    private final PessoaRepository pessoaRepository;
    private final TemaAulaRepository temaAulaRepository;

    public List<AulaResponseDTO> getAllAulas() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public AulaResponseDTO getAula(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula nao encontrada: " + id)));
    }

    public AulaResponseDTO saveAula(AulaRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toModel(dto)));
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

    @Override
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
                    localHoraFim = localHoraInicio.plusHours(2);
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

                // Verificar se o professor já está ocupado nesse horário em QUALQUER turma
                List<Aula> aulasDoProfessor = repository.findByInstrutorIdPessoaAndDataAula(professor.getIdPessoa(), dataFormatada);
                for (Aula aulaExistente : aulasDoProfessor) {
                    if (timeInicio.before(aulaExistente.getHoraFim()) && timeFim.after(aulaExistente.getHoraInicio())) {
                        throw new RuntimeException("Conflito: O Prof. " + professor.getNome() + " já está alocado na turma '"
                                + aulaExistente.getTurma().getNomeTurma() + "' neste horário.");
                    }
                }

                // Verificar se a TURMA já tem aula agendada nesse horário
                List<Aula> aulasDaTurma = repository.findByTurmaIdTurmaAndDataAula(turma.getIdTurma(), dataFormatada);
                for (Aula aulaExistente : aulasDaTurma) {
                    if (timeInicio.before(aulaExistente.getHoraFim()) && timeFim.after(aulaExistente.getHoraInicio())) {
                        throw new RuntimeException("Conflito: A turma '" + turma.getNomeTurma() + "' já possui a aula '"
                                + (aulaExistente.getTema() != null ? aulaExistente.getTema().getTituloTema() : "Sem Matéria") + "' neste horário.");
                    }
                }

                // Montando Aula
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
}