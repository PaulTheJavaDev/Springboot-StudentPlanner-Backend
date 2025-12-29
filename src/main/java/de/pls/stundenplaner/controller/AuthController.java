package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.dto.LoginRequest;
import de.pls.stundenplaner.dto.RegisterRequest;
import de.pls.stundenplaner.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST-Point handler for the Authentication process
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles the login process
     * @param loginRequest DTO containing username and password
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest loginRequest
    ) {

        final String successMessage = "Login successful!";
        final String failMessage = """
                Login Unsuccessful.
                Please make sure that Username and Password fields are correct.
                """;

        boolean success = authService.checkLogin(loginRequest);

        if (success) {
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        }

        return new ResponseEntity<>(failMessage, HttpStatus.UNAUTHORIZED);

    }

    /**
     * Registers a User. AuthService throws a {@link de.pls.stundenplaner.util.exceptions.UserAlreadyExistsException} if the username is already taken.
     * @param registerRequest DTO containing username and password
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {

        final String successMessage = "Register successful!";

        authService.registerUser(registerRequest);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);

    }
}
