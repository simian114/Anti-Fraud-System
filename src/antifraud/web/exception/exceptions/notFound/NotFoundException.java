package antifraud.web.exception.exceptions.notFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
