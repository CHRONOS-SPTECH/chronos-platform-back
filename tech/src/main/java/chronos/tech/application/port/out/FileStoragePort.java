package chronos.tech.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
    String uploadFile(MultipartFile file, String folder);
    String getFileUrl(String key);
    void deleteFile(String key);
    boolean validateFile(MultipartFile file);
}
