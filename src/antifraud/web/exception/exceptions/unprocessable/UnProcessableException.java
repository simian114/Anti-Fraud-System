package antifraud.web.exception.exceptions.unprocessable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnProcessableException extends RuntimeException {
    public UnProcessableException() {
        super("unprocessable entity exception");
    }

    public UnProcessableException(String message) {
        super(message);
    }
}
