package chronos.tech.domain.port;

import chronos.tech.domain.model.classes.*;

import java.time.LocalDate;
import java.util.List;

public interface HomeRepository {
    List<Turma> findActiveClasses();

    List<MatriculaTurma> findActiveEnrollments();

    List<Pessoa> findActiveStudents();

    List<Aula> findAllLessons();

    List<Aula> findAssignedLessons(Integer instructorId);

    List<Evento> findAllEvents();

    List<ChamadaAula> findCallsForLessons(List<Integer> lessonIds);

    List<Aula> findLessonsBetween(LocalDate start, LocalDate end);

    List<Aula> findAssignedLessonsBetween(Integer instructorId,
                                          LocalDate start,
                                          LocalDate end);

    List<Aula> findScheduledLessonsFrom(LocalDate start);

    List<Evento> findUpcomingEvents(LocalDate start,
                                    LocalDate end);

    List<Evento> findCompletedEvents(LocalDate start,
                                     LocalDate end);

    long countActiveClasses();

    long countActiveEnrollments();

    long countActiveStudents();

    long countLessonsBetween(LocalDate start,
                             LocalDate end);
}
