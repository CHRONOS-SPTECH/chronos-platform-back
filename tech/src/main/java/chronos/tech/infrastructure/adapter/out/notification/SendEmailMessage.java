package chronos.tech.infrastructure.adapter.out.notification;

import java.util.UUID;

public record SendEmailMessage(
        UUID messageId,
        String ownerRef,
        String from,
        String to,
        String subject,
        String body
) {
}
