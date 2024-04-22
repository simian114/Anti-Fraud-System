package antifraud.web.dto;

import antifraud.web.validator.annotation.CardNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StolenCardDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id; // response only

    @NotBlank
    @CardNumber
    private String number;
}
