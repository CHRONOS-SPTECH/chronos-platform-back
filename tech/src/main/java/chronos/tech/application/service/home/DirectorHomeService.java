package chronos.tech.application.service.home;

import chronos.tech.application.dto.home.director.DirectorHomeIndicatorsDTO;
import chronos.tech.application.dto.home.director.DirectorHomeResponseDTO;
import chronos.tech.application.dto.home.shared.HomeItemDTO;
import chronos.tech.application.dto.home.shared.HomeMetaDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Turma;
import chronos.tech.domain.port.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectorHomeService {
    private final HomeRepository homeRepository;
    private final HomeCalculationSupport calculations;

    public DirectorHomeResponseDTO build() {
        OffsetDateTime now = calculations.now();
        LocalDate today = now.toLocalDate();

        List<Turma> activeClasses = homeRepository.findActiveClasses();
        Set<Integer> activeClassIds = activeClasses.stream()
                .map(Turma::getIdTurma).collect(Collectors.toSet());
        List<MatriculaTurma> enrollments = homeRepository.findActiveEnrollments();
        Set<Integer> activeStudentIds = enrollments.stream()
                .filter(matricula -> activeClassIds.contains(matricula.getTurma().getIdTurma())
                        && calculations.isActiveStudent(matricula.getPessoa()))
                .map(matricula -> matricula.getPessoa().getIdPessoa())
                .collect(Collectors.toSet());

        List<Aula> lessons = homeRepository.findLessonsBetween(today.minusDays(29), today);
        List<Evento> upcomingEvents = calculations.upcomingEvents(now);
        List<Evento> completedEvents = calculations.completedEvents(today);
        List<Evento> completedThisMonth = completedEvents.stream()
                .filter(event -> YearMonth.from(event.getDataEvento()).equals(YearMonth.from(today)))
                .toList();

        long pendingAttendances = lessons.stream().filter(lesson -> calculations.isPending(lesson, now)).count();
        long recurringAbsences = calculations.recurringAbsences(lessons, today);

        List<HomeItemDTO> followUp = List.of(
                calculations.item("attendancePending", pendingAttendances,
                        lessons.stream().filter(lesson -> calculations.isPending(lesson, now))
                                .map(Aula::getIdAula).toList()),
                calculations.item("eventsCompleted", completedThisMonth.size(),
                        completedThisMonth.stream().map(Evento::getIdEvento).toList()),
                calculations.item("attendanceRisk", recurringAbsences, List.of()));

        long activeClassCount = homeRepository.countActiveClasses();

        return new DirectorHomeResponseDTO(
                new HomeMetaDTO("diretor", now, HomeCalculationSupport.ZONE.getId()),
                new DirectorHomeIndicatorsDTO(activeStudentIds.size(), activeClassCount,
                        upcomingEvents.size(), recurringAbsences),
                followUp,
                upcomingEvents.stream().limit(5).map(calculations::eventItem).toList(),
                calculations.eventSeries(completedEvents, today));
    }
}
