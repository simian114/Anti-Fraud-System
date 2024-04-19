package antifraud.web.controller;

import antifraud.constants.UpdateUserAccessOperationEnum;
import antifraud.service.UserService;
import antifraud.web.dto.StatusWithUsername;
import antifraud.web.dto.UpdateUserAccessDto;
import antifraud.web.dto.UpdateUserRoleDto;
import antifraud.web.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/api/auth/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.userService.registerUser(userDto));
    }

    @GetMapping("/api/auth/list")
    public ResponseEntity<?> getUserList() {
        return ResponseEntity
                .ok(this.userService.showAllUser());
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        this.userService.deleteUser(username);
        return ResponseEntity
                .ok(new StatusWithUsername("Deleted successfully!", username));
    }

    @PutMapping("/api/auth/access")
    public ResponseEntity<?> updateAccess(@Valid @RequestBody UpdateUserAccessDto updateUserAccessDto) {
        this.userService.updateUserAccess(updateUserAccessDto);
        return ResponseEntity
                .ok(Map.of("status", String.format("User %s %s!",
                                updateUserAccessDto.getUsername(),
                                updateUserAccessDto.getOperation().equals(UpdateUserAccessOperationEnum.LOCK)
                                        ? "locked"
                                        : "unlocked"
                        ))
                );
    }

    @PutMapping("/api/auth/role")
    public ResponseEntity<?> updateRole(@Valid @RequestBody UpdateUserRoleDto updateUserRoleDto) {
        //
        return ResponseEntity.ok(this.userService.updateUserRole(updateUserRoleDto));
    }
}
