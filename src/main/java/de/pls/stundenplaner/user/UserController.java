package de.pls.stundenplaner.user;

import de.pls.stundenplaner.auth.dto.request.ChangePasswordRequest;
import de.pls.stundenplaner.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody @Valid final User user
    ) {
        return userService.createUser(user);
    }

    @PostMapping("/me/changePassword")
    public ResponseEntity<User> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            @RequestHeader UUID sessionID
            ) {
        return userService.changePassword(sessionID, changePasswordRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsers(
            @PathVariable int id
    ) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable final int id
    ) {
        return userService.deleteUser(id);
    }

}