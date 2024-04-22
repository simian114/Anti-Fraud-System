package antifraud.web.dto;

import antifraud.constants.RegionCodeEnum;
import antifraud.constants.TransactionResultEnum;
import antifraud.web.validator.annotation.CardNumber;
import antifraud.web.validator.annotation.Ip;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long transactionId;

    @Min(1)
    private long amount; // ip regex
    @Ip
    private String ip;

    @CardNumber
    private String number;

    private RegionCodeEnum region;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    // response
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransactionResultEnum result;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransactionResultEnum feedback;

    public String getFeedback() {
        return this.feedback == null ? "" : this.feedback.name();
    }
}
