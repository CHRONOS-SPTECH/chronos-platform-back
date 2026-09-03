package chronos.tech.infrastructure.adapter.out.crypto;

import chronos.tech.application.port.out.CryptoPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AesCryptoAdapter implements CryptoPort {

    @Value("${crypto.encryption.key}")
    private String encryptionKey;

    private static final String CIPHER_ALGORITHM = "AES";

    @Override
    public String encrypt(String plainText) {
        try {
            if (plainText == null || plainText.isEmpty()) {
                throw new RuntimeException("Texto a criptografar não pode estar vazio");
            }

            byte[] decodedKey = Base64.getDecoder().decode(encryptionKey);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, CIPHER_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar biometria: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String encryptedText) {
        try {
            if (encryptedText == null || encryptedText.isEmpty()) {
                throw new RuntimeException("Texto a descriptografar não pode estar vazio");
            }

            byte[] decodedKey = Base64.getDecoder().decode(encryptionKey);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, CIPHER_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar biometria: " + e.getMessage(), e);
        }
    }
}
