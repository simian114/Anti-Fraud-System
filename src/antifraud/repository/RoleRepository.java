package antifraud.repository;

import antifraud.constants.RoleCodeEnum;
import antifraud.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(RoleCodeEnum code);
}
