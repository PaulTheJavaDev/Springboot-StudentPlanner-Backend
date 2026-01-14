package de.pls.stundenplaner.auth;

import de.pls.stundenplaner.auth.dto.request.LoginRequest;
import de.pls.stundenplaner.auth.dto.request.RegisterRequest;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.util.PasswordHasher;
import de.pls.stundenplaner.util.exceptions.auth.InvalidLoginException;
import de.pls.stundenplaner.util.exceptions.auth.UserAlreadyExistsException;
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
    public UUID checkLogin(
            final @NotNull LoginRequest loginRequest
    ) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(InvalidLoginException::new);

        String hashedInputPassword =
                PasswordHasher.sha256(loginRequest.getPassword());

        if (!hashedInputPassword.equals(user.getPassword_hash())) {
            throw new InvalidLoginException();
        }

        UUID sessionID = UUID.randomUUID();
        user.setSessionID(sessionID);
        userRepository.save(user);

        return sessionID;
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
