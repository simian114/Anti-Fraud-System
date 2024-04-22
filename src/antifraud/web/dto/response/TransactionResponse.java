package antifraud.web.dto.response;

import antifraud.constants.TransactionResultEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionResponse {
    private TransactionResultEnum result;
    private String info;
}
