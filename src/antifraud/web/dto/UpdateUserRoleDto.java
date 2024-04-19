package antifraud.web.dto;

import antifraud.constants.RoleCodeEnum;
import antifraud.web.validator.annotation.ValidRoles;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleDto {
    @NotBlank
    private String username;

    @ValidRoles(anyOf = {RoleCodeEnum.MERCHANT, RoleCodeEnum.SUPPORT})
    private RoleCodeEnum role;
}

