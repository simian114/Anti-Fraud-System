package antifraud.web.exception.exceptions.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException() {
        super("conflict detected");
    }

    public ConflictException(String message) {
        super(message);
    }
}
