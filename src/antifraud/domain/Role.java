package antifraud.domain;

import antifraud.constants.RoleCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Enumerated(EnumType.STRING)
    private RoleCodeEnum code;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return "ROLE_" + code.name();
    }
}
