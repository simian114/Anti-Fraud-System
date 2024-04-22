package antifraud.web.mapper;

import antifraud.domain.SuspiciousIp;
import antifraud.web.dto.SuspiciousIpDto;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousIpMapper {
    public SuspiciousIp toEntity(SuspiciousIpDto suspiciousIpDto) {
        SuspiciousIp suspiciousIp = new SuspiciousIp();
        suspiciousIp.setIp(suspiciousIpDto.getIp());
        return suspiciousIp;
    }

    public SuspiciousIpDto toDto(SuspiciousIp suspiciousIp) {
        SuspiciousIpDto suspiciousIpDto = new SuspiciousIpDto();
        suspiciousIpDto.setIp(suspiciousIp.getIp());
        suspiciousIpDto.setId(suspiciousIpDto.getId());
        return suspiciousIpDto;
    }
}
