package chronos.tech.infrastructure.adapter.in.event;

import chronos.tech.application.dto.notification.EventoEmailData;
import chronos.tech.application.event.EventoCriadoEvent;
import chronos.tech.application.port.out.EmailNotificationPort;
import chronos.tech.application.service.EventoEmailTemplateRenderer;
import chronos.tech.application.service.EventoNotificationDataService;
import chronos.tech.domain.model.classes.EventoDestinatario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventoCriadoNotificationListener {
    private final EventoNotificationDataService dataService;
    private final EventoEmailTemplateRenderer templateRenderer;
    private final EmailNotificationPort emailNotificationPort;

    @Async("eventNotificationExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoCriarEvento(EventoCriadoEvent event) {
        EventoEmailData dados = dataService.carregarDados(event.eventoId());
        String assunto = "Novo evento: " + dados.titulo();
        int enfileirados = 0;
        int falhas = 0;

        for (EventoDestinatario destinatario : dados.destinatarios()) {
            try {
                String html = templateRenderer.render(dados, destinatario.nome());
                emailNotificationPort.enqueue(
                        "evento-" + dados.eventoId(), destinatario.email(), assunto, html
                );
                enfileirados++;
            } catch (Exception exception) {
                falhas++;
                log.warn("Falha ao enfileirar e-mail do evento {} para um destinatário", dados.eventoId(), exception);
            }
        }

        log.info("Notificações do evento {}: {} enfileiradas, {} falhas", dados.eventoId(), enfileirados, falhas);
    }
}
