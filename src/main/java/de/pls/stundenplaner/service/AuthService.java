package de.pls.stundenplaner.service;

import de.pls.stundenplaner.dto.request.auth.LoginRequest;
import de.pls.stundenplaner.dto.request.auth.RegisterRequest;
import de.pls.stundenplaner.dto.response.auth.LoginResponse;
import de.pls.stundenplaner.repository.UserRepository;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.util.PasswordHasher;
import de.pls.stundenplaner.util.exceptions.InvalidLoginException;
import de.pls.stundenplaner.util.exceptions.UserAlreadyExistsException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Handles the Authentication process.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Login a user with given credentials.
     * Throws {@link InvalidLoginException} if username doesn't exist or password is wrong.
     */
    public LoginResponse checkLogin(
            final @NotNull LoginRequest loginRequest
    ) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(InvalidLoginException::new);

        String hashedInputPassword = PasswordHasher.sha256(loginRequest.getPassword());

        if (!hashedInputPassword.equals(user.getPassword_hash())) {
            throw new InvalidLoginException();
        }

        final UUID sessionID = UUID.randomUUID();
        user.setSessionID(sessionID);
        userRepository.save(user);

        return new LoginResponse(sessionID);
    }

    /**
     * Register a new user.
     * Throws {@link UserAlreadyExistsException} if username is already taken.
     */
    public void registerUser(
            final @NotNull RegisterRequest registerRequest
    ) {

        final String username = registerRequest.getUsername();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        final String hashedPassword =
                PasswordHasher.sha256(registerRequest.getPassword());

        final User user = new User(username, hashedPassword);
        userRepository.save(user);
    }
}
