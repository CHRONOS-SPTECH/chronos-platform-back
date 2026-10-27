package chronos.tech.application.service;

import chronos.tech.application.dto.notification.EventoEmailData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Component
public class EventoEmailTemplateRenderer {
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");
    private final String template;

    public EventoEmailTemplateRenderer() {
        try (var input = new ClassPathResource("templates/email/evento-novo.html").getInputStream()) {
            this.template = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível carregar o template de e-mail de evento", exception);
        }
    }

    public String render(EventoEmailData evento, String nomeDestinatario) {
        return template
                .replace("{{nome_destinatario}}", escape(nomeDestinatario))
                .replace("{{titulo}}", escape(evento.titulo()))
                .replace("{{descricao}}", escape(valorOu(evento.descricao(), "Mais informações em breve.")))
                .replace("{{local}}", escape(valorOu(evento.local(), "Local a confirmar")))
                .replace("{{data}}", evento.data().format(DATA))
                .replace("{{hora_inicio}}", evento.horaInicio().format(HORA))
                .replace("{{hora_fim}}", evento.horaFim().format(HORA));
    }

    private String escape(String value) {
        return HtmlUtils.htmlEscape(valorOu(value, ""));
    }

    private String valorOu(String valor, String padrao) {
        return valor == null || valor.isBlank() ? padrao : valor;
    }
}
