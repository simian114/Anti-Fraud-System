package antifraud.service;

import antifraud.constants.RoleCodeEnum;
import antifraud.constants.UpdateUserAccessOperationEnum;
import antifraud.domain.Role;
import antifraud.domain.User;
import antifraud.repository.RoleRepository;
import antifraud.repository.UserRepository;
import antifraud.web.dto.UpdateUserAccessDto;
import antifraud.web.dto.UpdateUserRoleDto;
import antifraud.web.dto.UserDto;
import antifraud.web.exception.exceptions.badRequest.BadRequestException;
import antifraud.web.exception.exceptions.conflict.ConflictException;
import antifraud.web.exception.exceptions.notFound.NotFoundException;
import antifraud.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserDto registerUser(UserDto userDto) {
        if (this.userRepository.existsByUsernameIgnoreCase(userDto.getUsername())) {
            throw new ConflictException();
        }
        User user = this.userMapper.toEntity(userDto);
        if (this.userRepository.count() == 0) {
            Role role = this.roleRepository.findByCode(RoleCodeEnum.ADMINISTRATOR).orElseGet(null);
            user.setLocked(false);
            user.setRole(role);
        } else {
            Role role = this.roleRepository.findByCode(RoleCodeEnum.MERCHANT).orElseGet(null);
            user.setRole(role);
        }
        return this.userMapper.toDto(this.userRepository.save(user));
    }

    public List<UserDto> showAllUser() {
        return this.userRepository.findAllByOrderByIdAsc().stream().
                map(this.userMapper::toDto).toList();
    }

    public void deleteUser(String username) {
        User user = this.userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        this.userRepository.delete(user);
    }

    public void updateUserAccess(UpdateUserAccessDto updateUserAccessDto) {
        User user = this.userRepository.findByUsernameIgnoreCase(updateUserAccessDto.getUsername())
                .orElseThrow(() -> new NotFoundException(updateUserAccessDto.getUsername() + " not found"));

        if (user.isAdmin()) {
            throw new BadRequestException();
        }

        UpdateUserAccessOperationEnum operation = updateUserAccessDto.getOperation();

        if (user.isLocked() && operation.equals(UpdateUserAccessOperationEnum.LOCK)
                || (!user.isLocked() && operation.equals(UpdateUserAccessOperationEnum.UNLOCK))
        ) {
            throw new ConflictException();
        }

        user.setLocked(
                operation.equals(UpdateUserAccessOperationEnum.LOCK)
        );
        this.userRepository.save(user);
    }

    public UserDto updateUserRole(UpdateUserRoleDto updateUserRoleDto) {
        User user = this.findUserElseThrowError(updateUserRoleDto.getUsername());
        if (user.isSameRoleByCode(updateUserRoleDto.getRole())) {
            throw new ConflictException("Role already exists");
        }
        user.setRole(this.findRoleElseThrowError(updateUserRoleDto.getRole()));
        this.userRepository.save(user);
        return userMapper.toDto(user);
    }

    private User findUserElseThrowError(final String username) throws NotFoundException {
        return this.userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException(username + " not found"));
    }

    private Role findRoleElseThrowError(final RoleCodeEnum code) throws NotFoundException {
        return this.roleRepository.findByCode(code).orElseThrow(() -> new NotFoundException(code + " not found"));
    }
}
