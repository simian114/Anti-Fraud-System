package antifraud.domain;

import antifraud.constants.RegionCodeEnum;
import antifraud.constants.TransactionResultEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(indexes = @Index(name = "Transaction__index", columnList = "ip, number"))
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    private long amount;

    private String ip;

    private String number;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @Enumerated(EnumType.STRING)
    private RegionCodeEnum region;

    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionResultEnum result;

    @Enumerated(EnumType.STRING)
    private TransactionResultEnum feedback;
}
