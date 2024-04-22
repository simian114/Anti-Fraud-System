package antifraud.web.dto;

import antifraud.web.validator.annotation.Ip;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuspiciousIpDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id; // response only

    @NotBlank
    @Ip
    private String ip;
}
