package antifraud.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    // request only
    @Min(1)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long amount;

    // response only
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String result;
}
