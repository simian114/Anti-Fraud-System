package antifraud.loader;

import antifraud.constants.RoleCodeEnum;
import antifraud.domain.Role;
import antifraud.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleLoader {
    private final RoleRepository roleRepository;

    public RoleLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        if (roleRepository.count() != 0) {
            return;
        }
        Role supportRole = new Role();
        supportRole.setCode(RoleCodeEnum.SUPPORT);
        this.roleRepository.save(supportRole);
        //
        Role merchantRole = new Role();
        merchantRole.setCode(RoleCodeEnum.MERCHANT);
        this.roleRepository.save(merchantRole);
        //
        Role adminRole = new Role();
        adminRole.setCode(RoleCodeEnum.ADMINISTRATOR);
        this.roleRepository.save(adminRole);
    }
}
