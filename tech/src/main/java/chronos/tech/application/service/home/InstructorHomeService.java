package chronos.tech.application.service.home;

import chronos.tech.application.dto.home.instructor.InstructorHomeIndicatorsDTO;
import chronos.tech.application.dto.home.instructor.InstructorHomeResponseDTO;
import chronos.tech.application.dto.home.instructor.HomeDayPointDTO;
import chronos.tech.application.dto.home.instructor.HomeScheduleDTO;
import chronos.tech.application.dto.home.instructor.HomeWeeklyHoursDTO;
import chronos.tech.application.dto.home.instructor.HomeWeeklyLessonsDTO;
import chronos.tech.application.dto.home.shared.HomeClassDTO;
import chronos.tech.application.dto.home.shared.HomeMetaDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.model.enums.StatusAula;
import chronos.tech.domain.port.HomeRepository;
import chronos.tech.infrastructure.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstructorHomeService {
    private final HomeRepository homeRepository;
    private final HomeCalculationSupport calculations;

    public InstructorHomeResponseDTO build(CustomUserPrincipal principal) {
        if (principal == null || principal.usuario().getPessoa() == null)
            throw new AccessDeniedException("Usuário sem pessoa vinculada");

        Integer instructorId = principal.usuario().getPessoa().getIdPessoa();
        OffsetDateTime now = calculations.now();
        LocalDate today = now.toLocalDate();
        LocalDate weekStart = calculations.weekStart(today);
        LocalDate weekEnd = weekStart.plusDays(6);

        List<Aula> assigned = homeRepository.findAssignedLessonsBetween(instructorId, weekStart, weekEnd).stream()
                .filter(lesson -> lesson.getStatusAula() != StatusAula.CANCELADA
                        && lesson.getStatusAula() != StatusAula.FERIADO)
                .toList();
        List<Aula> weekly = assigned.stream()
                .filter(lesson -> {
                    LocalDate date = calculations.dateOf(lesson);
                    return date != null && !date.isBefore(weekStart) && !date.isAfter(weekEnd);
                })
                .toList();
        List<Aula> todayLessons = weekly.stream()
                .filter(lesson -> today.equals(calculations.dateOf(lesson)))
                .sorted(Comparator.comparing(calculations::startAt))
                .toList();

        long closed = weekly.stream()
                .filter(lesson -> lesson.getStatusAula() == StatusAula.REALIZADA).count();
        long pending = weekly.stream().filter(lesson -> calculations.isPending(lesson, now)).count();
        long inProgress = weekly.stream().filter(lesson -> calculations.isInProgress(lesson, now)).count();
        long notStarted = weekly.stream().filter(lesson -> calculations.isFuture(lesson, now)).count();

        Set<Integer> activeClassIds = homeRepository.findActiveClasses().stream()
                .map(Turma::getIdTurma).collect(Collectors.toSet());
        Set<Integer> classIds = assigned.stream()
                .filter(lesson -> lesson.getTurma() != null
                        && activeClassIds.contains(lesson.getTurma().getIdTurma()))
                .map(lesson -> lesson.getTurma().getIdTurma())
                .collect(Collectors.toSet());
        long students = homeRepository.findActiveEnrollments().stream()
                .filter(matricula -> classIds.contains(matricula.getTurma().getIdTurma())
                        && calculations.isActiveStudent(matricula.getPessoa()))
                .map(matricula -> matricula.getPessoa().getIdPessoa())
                .distinct()
                .count();

        List<HomeDayPointDTO> points = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 7; i++) {
            LocalDate day = weekStart.plusDays(i);
            double hours = weekly.stream()
                    .filter(lesson -> day.equals(calculations.dateOf(lesson)))
                    .mapToDouble(calculations::durationHours)
                    .sum();
            total += hours;
            points.add(new HomeDayPointDTO(day, day.getDayOfWeek()
                    .getDisplayName(java.time.format.TextStyle.SHORT, Locale.forLanguageTag("pt-BR")), hours));
        }

        List<HomeScheduleDTO> schedule = todayLessons.stream()
                .map(lesson -> new HomeScheduleDTO(lesson.getIdAula(), calculations.startAt(lesson),
                        calculations.endAt(lesson), calculations.subject(lesson),
                        new HomeClassDTO(lesson.getTurma().getIdTurma(), lesson.getTurma().getNomeTurma()),
                        null, calculations.attendanceState(lesson, now)))
                .toList();

        return new InstructorHomeResponseDTO(
                new HomeMetaDTO("instrutor", now, HomeCalculationSupport.ZONE.getId(), instructorId),
                new InstructorHomeIndicatorsDTO(
                        new HomeWeeklyLessonsDTO(weekly.size(), closed, pending, inProgress, notStarted),
                        todayLessons.size(), classIds.size(), students),
                schedule,
                new HomeWeeklyHoursDTO("hours", total, weekStart, weekEnd, points));
    }
}
