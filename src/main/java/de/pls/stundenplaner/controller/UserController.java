package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody @Valid final User user
    ) {
        return userService.createUser(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return userService.getUsers();
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