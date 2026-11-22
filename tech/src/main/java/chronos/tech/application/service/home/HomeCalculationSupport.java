package chronos.tech.application.service.home;

import chronos.tech.application.dto.home.shared.HomeEventDTO;
import chronos.tech.application.dto.home.shared.HomeEventSeriesDTO;
import chronos.tech.application.dto.home.shared.HomeItemDTO;
import chronos.tech.application.dto.home.shared.HomeSeriesPointDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.ChamadaAula;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.enums.StatusAula;
import chronos.tech.domain.port.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HomeCalculationSupport {
    public static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");
    private static final Set<String> STUDENT_TYPES = Set.of("provacionista", "membro", "membro força viva");
    private static final int RECURRENT_ABSENCES = 3;

    private final HomeRepository homeRepository;

    public OffsetDateTime now() {
        return OffsetDateTime.now(ZONE);
    }

    public LocalDate weekStart(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public boolean isActiveStudent(Pessoa person) {
        return person != null
                && person.getDataSaida() == null
                && person.getTipoVinculo() != null
                && person.getTipoVinculo().getNome_vinculo() != null
                && STUDENT_TYPES.contains(person.getTipoVinculo().getNome_vinculo().toLowerCase(Locale.ROOT));
    }

    public List<Evento> upcomingEvents(OffsetDateTime now) {
        return homeRepository.findUpcomingEvents(now.toLocalDate(), now.plusDays(30).toLocalDate());
    }

    public List<Evento> completedEvents(LocalDate today) {
        LocalDate firstDay = YearMonth.from(today).minusMonths(4).atDay(1);
        return homeRepository.findCompletedEvents(firstDay, today);
    }

    public HomeEventSeriesDTO eventSeries(List<Evento> events, LocalDate today) {
        YearMonth first = YearMonth.from(today).minusMonths(4);
        Map<YearMonth, Long> counts = events.stream()
                .filter(event -> !YearMonth.from(event.getDataEvento()).isBefore(first))
                .collect(Collectors.groupingBy(event -> YearMonth.from(event.getDataEvento()),
                        TreeMap::new, Collectors.counting()));

        List<HomeSeriesPointDTO> series = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            YearMonth month = first.plusMonths(i);
            String label = month.getMonth().getDisplayName(java.time.format.TextStyle.SHORT,
                    Locale.forLanguageTag("pt-BR"));
            series.add(new HomeSeriesPointDTO(month.toString(), label,
                    counts.getOrDefault(month, 0L)));
        }
        return new HomeEventSeriesDTO("events", series.stream()
                .mapToLong(HomeSeriesPointDTO::value).sum(), series);
    }

    public HomeEventDTO eventItem(Evento event) {
        return new HomeEventDTO(event.getIdEvento(), event.getTitulo(), eventStart(event),
                event.getLocalEvento(), event.getIdCategoria() == null
                        ? null : event.getIdCategoria().getNome());
    }

    public HomeItemDTO item(String type, long count, List<Integer> ids) {
        return new HomeItemDTO(type, count, ids);
    }

    public List<Integer> lessonIds(List<Aula> lessons) {
        return lessons.stream().map(Aula::getIdAula).toList();
    }

    public boolean isPending(Aula lesson, OffsetDateTime now) {
        return lesson.getStatusAula() == StatusAula.AGENDADA
                && endAt(lesson) != null
                && endAt(lesson).isBefore(now);
    }

    public boolean isInProgress(Aula lesson, OffsetDateTime now) {
        return lesson.getStatusAula() == StatusAula.AGENDADA
                && startAt(lesson) != null
                && endAt(lesson) != null
                && !startAt(lesson).isAfter(now)
                && endAt(lesson).isAfter(now);
    }

    public boolean isFuture(Aula lesson, OffsetDateTime now) {
        return startAt(lesson) != null
                && !startAt(lesson).isBefore(now)
                && lesson.getStatusAula() == StatusAula.AGENDADA;
    }

    public String attendanceState(Aula lesson, OffsetDateTime now) {
        if (lesson.getStatusAula() == StatusAula.REALIZADA) return "realized";
        if (isPending(lesson, now)) return "attendancePending";
        if (isInProgress(lesson, now)) return "inProgress";
        return "notStarted";
    }

    public LocalDate dateOf(Aula lesson) {
        if (lesson.getDataAula() == null) return null;
        if (lesson.getDataAula() instanceof java.sql.Date sqlDate) return sqlDate.toLocalDate();
        return lesson.getDataAula().toInstant().atZone(ZONE).toLocalDate();
    }

    public OffsetDateTime startAt(Aula lesson) {
        return dateTime(dateOf(lesson), lesson.getHoraInicio());
    }

    public OffsetDateTime endAt(Aula lesson) {
        return dateTime(dateOf(lesson), lesson.getHoraFim());
    }

    public double durationHours(Aula lesson) {
        if (lesson.getHoraInicio() == null || lesson.getHoraFim() == null) return 0;
        long minutes = Duration.between(lesson.getHoraInicio().toLocalTime(),
                lesson.getHoraFim().toLocalTime()).toMinutes();
        return minutes > 0 ? minutes / 60.0 : 0;
    }

    public String subject(Aula lesson) {
        return lesson.getTema() == null ? null : lesson.getTema().getTituloTema();
    }

    public List<Integer> conflictingClassIds(List<Aula> lessons, OffsetDateTime now) {
        List<Aula> scheduled = lessons.stream()
                .filter(lesson -> lesson.getStatusAula() == StatusAula.AGENDADA
                        && startAt(lesson) != null
                        && endAt(lesson) != null
                        && !endAt(lesson).isBefore(now))
                .toList();

        Set<Integer> conflicts = new TreeSet<>();
        for (int i = 0; i < scheduled.size(); i++) {
            Aula first = scheduled.get(i);
            for (int j = i + 1; j < scheduled.size(); j++) {
                Aula second = scheduled.get(j);
                if (!Objects.equals(dateOf(first), dateOf(second)) || !overlaps(first, second)) continue;

                boolean sameClass = first.getTurma() != null && second.getTurma() != null
                        && Objects.equals(first.getTurma().getIdTurma(), second.getTurma().getIdTurma());
                boolean sameInstructor = first.getInstrutor() != null && second.getInstrutor() != null
                        && Objects.equals(first.getInstrutor().getIdPessoa(), second.getInstrutor().getIdPessoa());

                if (sameClass) conflicts.add(first.getTurma().getIdTurma());
                if (sameInstructor && first.getTurma() != null) conflicts.add(first.getTurma().getIdTurma());
                if (sameInstructor && second.getTurma() != null) conflicts.add(second.getTurma().getIdTurma());
            }
        }
        return List.copyOf(conflicts);
    }

    public long recurringAbsences(List<Aula> lessons, LocalDate today) {
        LocalDate from = today.minusDays(29);
        List<Integer> realizedLessonIds = lessons.stream()
                .filter(lesson -> {
                    LocalDate day = dateOf(lesson);
                    return day != null && !day.isBefore(from) && !day.isAfter(today)
                            && lesson.getStatusAula() == StatusAula.REALIZADA;
                })
                .map(Aula::getIdAula)
                .toList();

        Map<Integer, List<ChamadaAula>> callsByStudent = new HashMap<>();
        for (ChamadaAula call : homeRepository.findCallsForLessons(realizedLessonIds)) {
            if (call.getPessoa() == null) continue;
            callsByStudent.computeIfAbsent(call.getPessoa().getIdPessoa(), ignored -> new ArrayList<>()).add(call);
        }

        return callsByStudent.values().stream()
                .filter(calls -> calls.stream()
                        .sorted(Comparator.comparing((ChamadaAula call) -> dateOf(call.getAula()),
                                Comparator.nullsLast(Comparator.naturalOrder())))
                        .reduce(new int[]{0, 0}, (state, call) -> {
                            if (Boolean.FALSE.equals(call.getCompareceu())) state[0]++;
                            else state[0] = 0;
                            state[1] = Math.max(state[1], state[0]);
                            return state;
                        }, (left, right) -> left)[1] >= RECURRENT_ABSENCES)
                .count();
    }

    private OffsetDateTime dateTime(LocalDate date, Time time) {
        return date == null || time == null
                ? null : date.atTime(time.toLocalTime()).atZone(ZONE).toOffsetDateTime();
    }

    private OffsetDateTime eventStart(Evento event) {
        return event.getDataEvento().atTime(event.getHoraInicioEvento()).atZone(ZONE).toOffsetDateTime();
    }

    private boolean overlaps(Aula first, Aula second) {
        return startAt(first).isBefore(endAt(second)) && startAt(second).isBefore(endAt(first));
    }
}
