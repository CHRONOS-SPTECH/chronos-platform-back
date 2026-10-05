package chronos.tech.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface CryptoPort {
    String encrypt(String plainText);
    String decrypt(String encryptedText);
}
