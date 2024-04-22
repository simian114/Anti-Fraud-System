package antifraud.web.dto;

import antifraud.constants.TransactionResultEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionFeedbackDto {
    private long transactionId;
    private TransactionResultEnum feedback;
}
