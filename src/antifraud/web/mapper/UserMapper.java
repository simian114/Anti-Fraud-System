package antifraud.web.mapper;

import antifraud.domain.User;
import antifraud.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().getCode());
        return userDto;
    }
}
