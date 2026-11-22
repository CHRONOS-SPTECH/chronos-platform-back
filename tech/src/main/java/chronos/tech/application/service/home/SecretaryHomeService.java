package chronos.tech.application.service.home;

import chronos.tech.application.dto.home.secretary.SecretaryHomeIndicatorsDTO;
import chronos.tech.application.dto.home.secretary.SecretaryHomeResponseDTO;
import chronos.tech.application.dto.home.secretary.UnscheduledInstructorDTO;
import chronos.tech.application.dto.home.secretary.UnscheduledLessonDTO;
import chronos.tech.application.dto.home.shared.HomeClassDTO;
import chronos.tech.application.dto.home.shared.HomeEventDTO;
import chronos.tech.application.dto.home.shared.HomeItemDTO;
import chronos.tech.application.dto.home.shared.HomeMetaDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.port.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecretaryHomeService {
    private final HomeRepository homeRepository;
    private final HomeCalculationSupport calculations;

    public SecretaryHomeResponseDTO build() {
        OffsetDateTime now = calculations.now();
        LocalDate today = now.toLocalDate();
        LocalDate weekStart = calculations.weekStart(today);
        LocalDate weekEnd = weekStart.plusDays(6);

        Set<Integer> placedStudentIds = homeRepository.findActiveEnrollments().stream()
                .map(matricula -> matricula.getPessoa().getIdPessoa())
                .collect(Collectors.toSet());

        List<Aula> lessons = homeRepository.findScheduledLessonsFrom(today);
        List<Aula> unscheduledLessons = homeRepository.findAllLessons().stream()
                .filter(lesson -> lesson.getDataAula() == null
                        || lesson.getHoraInicio() == null
                        || lesson.getHoraFim() == null)
                .sorted(Comparator.comparing(Aula::getIdAula, Comparator.nullsLast(Comparator.naturalOrder())))
                .limit(10)
                .toList();

        List<HomeEventDTO> upcomingEvents = calculations.upcomingEvents(now).stream()
                .limit(5).map(calculations::eventItem).toList();
        List<Integer> unplacedStudentIds = homeRepository.findActiveStudents().stream()
                .map(Pessoa::getIdPessoa)
                .filter(id -> !placedStudentIds.contains(id))
                .toList();
        List<Integer> conflictClassIds = calculations.conflictingClassIds(lessons, now);

        List<HomeItemDTO> operationalQueue = List.of(
                calculations.item("attendancePending",
                        lessons.stream().filter(lesson -> calculations.isPending(lesson, now)).count(),
                        calculations.lessonIds(lessons.stream()
                                .filter(lesson -> calculations.isPending(lesson, now)).toList())),
                calculations.item("studentAwaitingPlacement", unplacedStudentIds.size(), unplacedStudentIds),
                calculations.item("scheduleConflict", conflictClassIds.size(), conflictClassIds));

        long weeklyLessonCount = homeRepository.countLessonsBetween(weekStart, weekEnd);
        long activeEnrollmentCount = homeRepository.countActiveEnrollments();
        long activeClassCount = homeRepository.countActiveClasses();

        return new SecretaryHomeResponseDTO(
                new HomeMetaDTO("secretaria", now, HomeCalculationSupport.ZONE.getId()),
                new SecretaryHomeIndicatorsDTO(activeEnrollmentCount, activeClassCount,
                        weeklyLessonCount, unplacedStudentIds.size()),
                operationalQueue,
                unscheduledLessons.stream()
                        .map(lesson -> new UnscheduledLessonDTO(
                                lesson.getIdAula(),
                                calculations.subject(lesson),
                                calculations.startAt(lesson),
                                calculations.endAt(lesson),
                                lesson.getTurma() == null ? null
                                        : new HomeClassDTO(lesson.getTurma().getIdTurma(), lesson.getTurma().getNomeTurma()),
                                null,
                                lesson.getInstrutor() == null ? null
                                        : new UnscheduledInstructorDTO(lesson.getInstrutor().getIdPessoa(), lesson.getInstrutor().getNome())))
                        .toList(),
                upcomingEvents);
    }
}
