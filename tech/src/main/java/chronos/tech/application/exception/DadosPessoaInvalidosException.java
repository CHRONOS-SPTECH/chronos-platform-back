package chronos.tech.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DadosPessoaInvalidosException extends RuntimeException {
    public DadosPessoaInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }

    public DadosPessoaInvalidosException(String message) {
        super(message);
    }
}
