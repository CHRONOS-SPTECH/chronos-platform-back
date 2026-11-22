package chronos.tech.infrastructure.adapter.out.persistence;

import chronos.tech.domain.model.classes.*;
import chronos.tech.domain.model.enums.StatusTurma;
import chronos.tech.domain.port.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HomeRepositoryAdapter implements HomeRepository {
    private final SpringDataTurmaRepository turmaRepository;
    private final SpringDataMatriculaTurmaRepository matriculaRepository;
    private final SpringDataPessoaRepository pessoaRepository;
    private final SpringDataAulaRepository aulaRepository;
    private final SpringDataEventoRepository eventoRepository;
    private final SpringDataChamadaAulaRepository chamadaRepository;

    @Override
    public List<Turma> findActiveClasses() {
        return turmaRepository.findByStatusTurma(StatusTurma.EM_ANDAMENTO);
    }

    @Override
    public List<MatriculaTurma> findActiveEnrollments() {
        return matriculaRepository.findActiveEnrollments(StatusTurma.EM_ANDAMENTO);
    }

    @Override
    public List<Pessoa> findActiveStudents() {
        return pessoaRepository.findActiveStudents();
    }

    @Override
    public List<Aula> findAllLessons() {
        return aulaRepository.findAll();
    }

    @Override
    public List<Aula> findAssignedLessons(Integer instructorId) {
        return aulaRepository.findByInstrutorIdPessoa(instructorId);
    }

    @Override
    public List<Evento> findAllEvents() {
        return eventoRepository.findAll();
    }

    @Override
    public List<ChamadaAula> findCallsForLessons(List<Integer> lessonIds) {
        if (lessonIds.isEmpty()) return List.of();
        List<Long> ids = lessonIds.stream().map(Integer::longValue).toList();
        return chamadaRepository.findByAulaIdAulaIn(ids);
    }

    @Override
    public List<Aula> findLessonsBetween(LocalDate start, LocalDate end) {
        return aulaRepository.findLessonsBetween(Date.valueOf(start), Date.valueOf(end));
    }

    @Override
    public List<Aula> findAssignedLessonsBetween(Integer instructorId, LocalDate start, LocalDate end) {
        return aulaRepository.findAssignedLessonsBetween(instructorId, Date.valueOf(start), Date.valueOf(end));
    }

    @Override
    public List<Aula> findScheduledLessonsFrom(LocalDate start) {
        return aulaRepository.findScheduledLessonsFrom(Date.valueOf(start));
    }

    @Override
    public List<Evento> findUpcomingEvents(LocalDate start, LocalDate end) {
        return eventoRepository.findUpcomingEvents(start, end);
    }

    @Override
    public List<Evento> findCompletedEvents(LocalDate start, LocalDate end) {
        return eventoRepository.findCompletedEvents(start, end);
    }

    @Override
    public long countActiveClasses() {
        return turmaRepository.countByStatus(StatusTurma.EM_ANDAMENTO);
    }

    @Override
    public long countActiveEnrollments() {
        return matriculaRepository.countActiveEnrollments(StatusTurma.EM_ANDAMENTO);
    }

    @Override
    public long countActiveStudents() {
        return pessoaRepository.countActiveStudents();
    }

    @Override
    public long countLessonsBetween(LocalDate start, LocalDate end) {
        return aulaRepository.countLessonsBetween(Date.valueOf(start), Date.valueOf(end));
    }
}
