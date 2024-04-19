package antifraud.web.dto;

import antifraud.constants.RoleCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // response only
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // request only
    private String password;

    // response only
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private RoleCodeEnum role;
}
