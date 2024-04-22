package antifraud.constants;

import lombok.Getter;

@Getter
public enum TransactionInfoEnum {
    NONE("none"),
    AMOUNT("amount"),
    IP_CORRELATION("ip-correlation"),
    REGION_CORRELATION("region-correlation"),
    IP("ip"),
    CARD_NUMBER("card-number");

    private final String value;

    TransactionInfoEnum(String value) {
        this.value = value;
    }
}
