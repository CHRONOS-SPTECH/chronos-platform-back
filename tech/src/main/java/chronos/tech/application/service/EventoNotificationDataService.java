package chronos.tech.application.service;

import chronos.tech.application.dto.notification.EventoEmailData;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.EventoDestinatario;
import chronos.tech.domain.port.EventoRepository;
import chronos.tech.domain.port.MatriculaTurmaRepository;
import chronos.tech.domain.port.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoNotificationDataService {
    private static final ZoneId BUSINESS_ZONE = ZoneId.of("America/Sao_Paulo");

    private final EventoRepository eventoRepository;
    private final MatriculaTurmaRepository matriculaTurmaRepository;
    private final PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public EventoEmailData carregarDados(Integer eventoId) {
        Evento evento = eventoRepository.findById(eventoId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + eventoId));

        LocalDate hoje = LocalDate.now(BUSINESS_ZONE);
        List<Integer> turmaIds = evento.getTurmas() == null
                ? List.of()
                : evento.getTurmas().stream().map(turma -> turma.getIdTurma()).toList();

        List<EventoDestinatario> destinatarios = new ArrayList<>(
                matriculaTurmaRepository.findDestinatariosAtivosEmTurmasIniciadas(turmaIds, hoje)
        );
        if (Boolean.TRUE.equals(evento.getIncluirPublicoExterno())) {
            destinatarios.addAll(pessoaRepository.findPublicoExternoAtivoSemMatricula());
        }

        return new EventoEmailData(
                evento.getIdEvento(), evento.getTitulo(), evento.getDescricao(), evento.getLocalEvento(),
                evento.getDataEvento(), evento.getHoraInicioEvento(), evento.getHoraFimEventos(), destinatarios
        );
    }
}
