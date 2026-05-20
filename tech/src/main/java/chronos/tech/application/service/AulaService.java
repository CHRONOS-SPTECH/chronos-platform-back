package chronos.tech.application.service;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.response.AulaComAlunosResponseDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.application.dto.response.AlunoComPresencaDTO;
import chronos.tech.application.dto.response.TurmaSimplificadaDTO;
import chronos.tech.application.mapper.AulaMapper;
import chronos.tech.application.port.in.AulaUseCase;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AulaService implements AulaUseCase {
    private final AulaRepository repository;
    private final AulaMapper mapper;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;

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

    public List<AulaComAlunosResponseDTO> getAulasDoDiaComAlunos(Date data, Integer instrutorId) {
        List<Aula> aulasDoDia = repository.findByDataAulaAndInstrutorIdPessoa(data, instrutorId);

        return aulasDoDia.stream().map(aula -> {
            Turma turma = aula.getTurma();
            String nomeAula = aula.getTema() != null ? aula.getTema().getTituloTema() : null;

            TurmaSimplificadaDTO turmaDTO = null;
            List<AlunoComPresencaDTO> alunos = List.of();
            
            if (turma != null) {
                turmaDTO = new TurmaSimplificadaDTO(
                    turma.getIdTurma(),
                    turma.getNomeTurma(),
                    turma.getDataInicio(),
                    turma.getDataEncerramento()
                );

                List<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaIdTurma(turma.getIdTurma());
                
                alunos = matriculas.stream()
                        .map(m -> {
                            Integer pessoaId = m.getPessoa().getIdPessoa();
                            
                            List<ChamadaAula> chamadasAluno = chamadaAulaRepository
                                .findByPessoaIdPessoaAndAulaTurmaIdTurma(pessoaId, turma.getIdTurma());
                            
                            double percentualPresenca = 0.0;
                            if (!chamadasAluno.isEmpty()) {
                                long compareceuCount = chamadasAluno.stream()
                                    .filter(c -> c.getCompareceu() != null && c.getCompareceu())
                                    .count();
                                percentualPresenca = (compareceuCount * 100.0) / chamadasAluno.size();
                            }
                            
                            return new AlunoComPresencaDTO(
                                pessoaId,
                                m.getPessoa().getNome(),
                                m.getPessoa().getCpf(),
                                percentualPresenca
                            );
                        })
                        .collect(Collectors.toList());
            }

            return new AulaComAlunosResponseDTO(
                    aula.getIdAula(),
                    nomeAula,
                    aula.getDataAula(),
                    aula.getHoraInicio(),
                    aula.getHoraFim(),
                    aula.getStatusAula(),
                    aula.getData_criacao_registro(),
                    turmaDTO,
                    alunos
            );
        }).collect(Collectors.toList());
    }
}
