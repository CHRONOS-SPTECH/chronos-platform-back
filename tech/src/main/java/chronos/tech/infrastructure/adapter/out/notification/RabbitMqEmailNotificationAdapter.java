package chronos.tech.infrastructure.adapter.out.notification;

import chronos.tech.application.port.out.EmailNotificationPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitMqEmailNotificationAdapter implements EmailNotificationPort {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String exchange;
    private final String routingKey;
    private final String emailFrom;

    public RabbitMqEmailNotificationAdapter(
            RabbitTemplate rabbitTemplate,
            @Value("${chronos.email.rabbitmq.exchange}") String exchange,
            @Value("${chronos.email.rabbitmq.routing-key}") String routingKey,
            @Value("${chronos.email.from}") String emailFrom
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.emailFrom = emailFrom;
    }

    @Override
    public void enqueue(String ownerRef, String emailTo, String subject, String htmlBody) {
        SendEmailMessage payload = new SendEmailMessage(
                UUID.randomUUID(), ownerRef, emailFrom, emailTo, subject, htmlBody
        );

        try {
            MessageProperties properties = new MessageProperties();
            properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            rabbitTemplate.send(exchange, routingKey,
                    new Message(objectMapper.writeValueAsBytes(payload), properties));
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Não foi possível serializar a solicitação de e-mail", exception);
        }
    }
}
