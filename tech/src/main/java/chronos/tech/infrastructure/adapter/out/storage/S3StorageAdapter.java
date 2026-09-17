package chronos.tech.infrastructure.adapter.out.storage;

import chronos.tech.application.port.out.FileStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class S3StorageAdapter implements FileStoragePort {

    private static final Logger logger = LoggerFactory.getLogger(S3StorageAdapter.class);

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    private static final long MAX_FILE_SIZE = 10L * 1024L * 1024L; // 10 MB
    private static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png"};
    private static final String[] ALLOWED_BIOMETRIC_TYPES = {"image/jpeg", "image/png", "application/pdf", "application/json"};

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        try {
            if (!validateFile(file)) {
                logger.warn("[S3] arquivo inválido ou tipo não permitido: name={}, size={}, contentType={}", file.getOriginalFilename(), file.getSize(), file.getContentType());
                throw new RuntimeException("Arquivo inválido ou tipo não permitido");
            }

            String fileName = generateFileName(file.getOriginalFilename());
            String key = folder + "/" + fileName;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = getFileUrl(key);
            return url;

        } catch (IOException e) {
            logger.error("[S3] erro ao ler arquivo", e);
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[S3] erro ao fazer upload para S3", e);
            throw new RuntimeException("Erro ao fazer upload para S3: " + e.getMessage(), e);
        }
    }

    @Override
    public String getFileUrl(String key) {
        try {
            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .region(software.amazon.awssdk.regions.Region.of(region))
                    .bucket(bucketName)
                    .key(key)
                    .build();

            return s3Client.utilities().getUrl(getUrlRequest).toString();

        } catch (Exception e) {
            logger.error("[S3] erro ao gerar URL do arquivo", e);
            throw new RuntimeException("Erro ao gerar URL do arquivo: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            logger.error("[S3] erro ao deletar arquivo do S3", e);
            throw new RuntimeException("Erro ao deletar arquivo do S3: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            logger.warn("[S3] arquivo vazio ou null");
            return false;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            logger.warn("[S3] arquivo muito grande: size={} max={}", file.getSize(), MAX_FILE_SIZE);
            return false;
        }

        String mimeType = file.getContentType();
        for (String type : ALLOWED_IMAGE_TYPES) {
            if (type.equals(mimeType)) return true;
        }
        for (String type : ALLOWED_BIOMETRIC_TYPES) {
            if (type.equals(mimeType)) return true;
        }

        logger.warn("[S3] mimeType não permitido: {}", mimeType);
        return false;
    }

    private String generateFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }
}