package antifraud.web.mapper;

import antifraud.domain.StolenCard;
import antifraud.web.dto.StolenCardDto;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public StolenCard toEntity(StolenCardDto stolenCardDto) {
        StolenCard stolenCard = new StolenCard();
        stolenCard.setNumber(stolenCardDto.getNumber());
        return stolenCard;
    }

    public StolenCardDto toDto(StolenCard stolenCard) {
        StolenCardDto stolenCardDto = new StolenCardDto();
        stolenCardDto.setId(stolenCard.getId());
        stolenCardDto.setNumber(stolenCard.getNumber());
        return stolenCardDto;
    }
}
