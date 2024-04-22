package antifraud.constants;

import lombok.Getter;

@Getter
public enum TransactionResultEnum {
    ALLOWED(1),
    MANUAL_PROCESSING(2),
    PROHIBITED(3);

    private final int level;

    TransactionResultEnum(int level) {
        this.level = level;
    }

    public int getDifferenceLevel(TransactionResultEnum other) {
        return this.level - other.level;
    }
}
