package de.pls.stundenplaner.auth;

import de.pls.stundenplaner.auth.dto.request.LoginRequest;
import de.pls.stundenplaner.auth.dto.request.RegisterRequest;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.util.PasswordHasher;
import de.pls.stundenplaner.auth.exceptions.InvalidLoginException;
import de.pls.stundenplaner.auth.exceptions.UserAlreadyExistsException;
import io.micrometer.common.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public UUID checkLogin(LoginRequest loginRequest) {

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
     *
     * @param registerRequest DTO containing username and password
     */
    public void registerUser(
            RegisterRequest registerRequest
    ) {

        final String username = registerRequest.getUsername();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        final String hashed_Password = PasswordHasher.sha256(registerRequest.getPassword());
        final User userForRegister = new User(username, hashed_Password);

        userRepository.save(userForRegister);
    }

}