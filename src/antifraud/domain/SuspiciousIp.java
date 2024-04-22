package antifraud.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SuspiciousIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long suspiciousIpId;

    private String ip;
}
