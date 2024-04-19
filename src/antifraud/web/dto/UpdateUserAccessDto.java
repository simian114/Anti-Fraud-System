package antifraud.web.dto;

import antifraud.constants.UpdateUserAccessOperationEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserAccessDto {

    @NotBlank
    private String username;

    @Enumerated(EnumType.STRING)
    private UpdateUserAccessOperationEnum operation;
}
