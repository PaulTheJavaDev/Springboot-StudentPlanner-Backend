package de.pls.stundenplaner.auth;

import de.pls.stundenplaner.auth.dto.request.LoginRequest;
import de.pls.stundenplaner.auth.dto.request.RegisterRequest;
import de.pls.stundenplaner.auth.dto.response.LoginResponse;
import de.pls.stundenplaner.util.exceptions.auth.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

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
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {

        UUID sessionID = authService.checkLogin(loginRequest);
        return new ResponseEntity<>(new LoginResponse(sessionID), HttpStatus.OK);

    }

    /**
     * Registers a User. AuthService throws a {@link UserAlreadyExistsException} if the username is already taken.
     * @param registerRequest DTO containing username and password
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {

        authService.registerUser(registerRequest);
        return new ResponseEntity<>("Register successful!", HttpStatus.OK);

    }
}
