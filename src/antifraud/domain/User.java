package antifraud.domain;

import antifraud.constants.RoleCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean locked = true;

    @OneToOne
    private Role role;

    // domain logic

    @JsonIgnore
    public boolean isAdmin() {
        return this.role.getCode().equals(RoleCodeEnum.ADMINISTRATOR);
    }

    @JsonIgnore
    public boolean isSameRoleByCode(RoleCodeEnum roleCode) {
        return this.role.getCode().equals(roleCode);
    }
}
