package antifraud.web.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponse {
    private String status;

    public StatusResponse(String status) {
        this.status = status;
    }
}
