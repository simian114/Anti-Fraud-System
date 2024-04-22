package antifraud.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusWithUsernameResponse {
    private String status;
    private String username;
}
