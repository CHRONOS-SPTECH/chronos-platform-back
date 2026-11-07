package chronos.tech.application.service;

import chronos.tech.application.dto.turma.TurmaDetalhesResponseDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusAula;
import chronos.tech.domain.port.AulaRepository;
import chronos.tech.domain.port.ChamadaAulaRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurmaDetalhesService {
    private static final ZoneId APP_ZONE = ZoneId.of("America/Sao_Paulo");
    private static final Set<String> VINCULOS_ALUNO = Set.of("Provacionista", "Membro", "Membro Força Viva");

    private final TurmaRepository turmaRepository;
    private final AulaRepository aulaRepository;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final ChamadaAulaRepository chamadaAulaRepository;

    @Transactional(readOnly = true)
    public TurmaDetalhesResponseDTO obter(Long idTurma) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Turma não encontrada: " + idTurma));
        Integer turmaId = turma.getIdTurma();

        List<MatriculaTurma> matriculas = matriculaTurmaRepository.findByTurmaIdTurma(turmaId);
        Set<Integer> alunosElegiveis = matriculas.stream()
                .map(MatriculaTurma::getPessoa)
                .filter(this::alunoElegivelAtivo)
                .map(Pessoa::getIdPessoa)
                .collect(Collectors.toSet());

        List<Aula> aulas = aulaRepository.findByTurmaIdTurma(turmaId);
        List<Aula> aulasPlanejadas = aulas.stream().filter(this::aulaPlanejada).toList();
        Set<Integer> idsAulasPlanejadas = aulasPlanejadas.stream()
                .map(Aula::getIdAula)
                .collect(Collectors.toSet());
        List<ChamadaAula> chamadas = chamadaAulaRepository.findByAulaTurmaIdTurma(turmaId);

        Set<Integer> idsAulasRealizadas = chamadas.stream()
                .filter(chamada -> chamada.getAula() != null
                        && idsAulasPlanejadas.contains(chamada.getAula().getIdAula()))
                .map(chamada -> chamada.getAula().getIdAula())
                .collect(Collectors.toSet());

        List<ChamadaAula> chamadasComPresenca = chamadas.stream()
                .filter(chamada -> chamada.getAula() != null
                        && idsAulasPlanejadas.contains(chamada.getAula().getIdAula()))
                .filter(chamada -> chamada.getPessoa() != null
                        && alunosElegiveis.contains(chamada.getPessoa().getIdPessoa()))
                .filter(chamada -> chamada.getCompareceu() != null)
                .toList();

        Double percentualPresencaMedia = aulasPlanejadas.isEmpty() || chamadasComPresenca.isEmpty()
                ? null
                : chamadasComPresenca.stream().filter(chamada -> Boolean.TRUE.equals(chamada.getCompareceu())).count()
                    * 100.0 / chamadasComPresenca.size();

        Aula proximaAula = aulas.stream()
                .filter(aula -> aula.getStatusAula() == StatusAula.AGENDADA)
                .filter(aula -> aula.getDataAula() != null && aula.getHoraInicio() != null)
                .filter(aula -> dataHora(aula).isAfter(OffsetDateTime.now(APP_ZONE)))
                .min(Comparator.comparing(this::dataHora))
                .orElse(null);

        return new TurmaDetalhesResponseDTO(
                turma.getIdTurma().longValue(),
                turma.getNomeTurma(),
                turma.getDataInicio(),
                turma.getDataEncerramento(),
                turma.getStatusTurma(),
                turma.calcularPercentualConclusao(),
                new TurmaDetalhesResponseDTO.IndicadoresDTO(
                        alunosElegiveis.size(), percentualPresencaMedia,
                        idsAulasRealizadas.size(), aulasPlanejadas.size()),
                proximaAula == null ? null : toProximaAula(proximaAula),
                proximaAula == null || proximaAula.getInstrutor() == null
                        ? null : toResponsavel(proximaAula.getInstrutor()));
    }

    private boolean alunoElegivelAtivo(Pessoa pessoa) {
        return pessoa != null && pessoa.getDataSaida() == null && pessoa.getTipoVinculo() != null
                && VINCULOS_ALUNO.contains(pessoa.getTipoVinculo().getNome_vinculo());
    }

    private boolean aulaPlanejada(Aula aula) {
        return aula.getStatusAula() != StatusAula.CANCELADA && aula.getStatusAula() != StatusAula.FERIADO;
    }

    private OffsetDateTime dataHora(Aula aula) {
        LocalDate data = aula.getDataAula() instanceof java.sql.Date sqlDate
                ? sqlDate.toLocalDate()
                : Instant.ofEpochMilli(aula.getDataAula().getTime())
                    .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime dataHoraLocal = data.atTime(aula.getHoraInicio().toLocalTime());
        return dataHoraLocal.atZone(APP_ZONE).toOffsetDateTime();
    }

    private TurmaDetalhesResponseDTO.ProximaAulaDTO toProximaAula(Aula aula) {
        OffsetDateTime inicio = dataHora(aula);
        OffsetDateTime fim = aula.getHoraFim() == null ? null
                : inicio.toLocalDate().atTime(aula.getHoraFim().toLocalTime()).atZone(APP_ZONE).toOffsetDateTime();
        return new TurmaDetalhesResponseDTO.ProximaAulaDTO(
                aula.getIdAula(), inicio, fim,
                aula.getTema() == null ? null : aula.getTema().getTituloTema());
    }

    private TurmaDetalhesResponseDTO.ResponsavelDTO toResponsavel(Pessoa pessoa) {
        return new TurmaDetalhesResponseDTO.ResponsavelDTO(
                pessoa.getIdPessoa().longValue(), pessoa.getNome(), pessoa.getEmail());
    }
}
