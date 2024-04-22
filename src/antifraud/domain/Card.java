package antifraud.domain;

import antifraud.constants.TransactionResultEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Card {
    @Id
    private String number;
    private long allowLimit = 200;
    private long manualLimit = 1500;
    public Card(String number) {
        this.number = number;
    }

    // domain logic

    public void changeLimit(TransactionResultEnum from, TransactionResultEnum to, long value) {
        int differenceLevel = to.getDifferenceLevel(from);
        if (differenceLevel > 0) {
            // allow -> manual_process -> prohibited
            // decrease
            if (from.getLevel() == 1) {
                this.decreaseAllowLimit(value);
                if (to.getLevel() == 3) {
                    from = TransactionResultEnum.MANUAL_PROCESSING;
                }
            }
            if (from.getLevel() == 2) {
                this.decreaseManualProcessingLimit(value);
            }
        } else {
            // prohibited -> manual_process -> allow
            // increase
            if (from.getLevel() == 3) {
                this.increaseManualProcessingLimit(value);
                if (to.getLevel() == 1) {
                    from = TransactionResultEnum.MANUAL_PROCESSING;
                }
            }
            if (from.getLevel() == 2) {
                this.increaseAllowLimit(value);
            }
        }
        log.info("CARD NUMBER: {}", this.number);
        log.info("CHANGE ALLOW LIMIT: {}", this.allowLimit);
        log.info("CHANGE MANUAL LIMIT: {}", this.manualLimit);
    }


    private void increaseAllowLimit(long value) {
        this.allowLimit = (long) Math.ceil(0.8 * allowLimit + 0.2 * value);
    }


    private void decreaseAllowLimit(long value) {
        this.allowLimit = (long) Math.ceil(0.8 * allowLimit - 0.2 * value);
    }


    private void increaseManualProcessingLimit(long value) {
        this.manualLimit = (long) Math.ceil(0.8 * manualLimit + 0.2 * value);
    }


    private void decreaseManualProcessingLimit(long value) {
        this.manualLimit = (long) Math.ceil(0.8 * manualLimit - 0.2 * value);
    }


}
