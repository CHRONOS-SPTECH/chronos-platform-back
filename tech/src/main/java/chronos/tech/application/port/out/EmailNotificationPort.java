package chronos.tech.application.port.out;

public interface EmailNotificationPort {
    void enqueue(String ownerRef, String emailTo, String subject, String htmlBody);
}
