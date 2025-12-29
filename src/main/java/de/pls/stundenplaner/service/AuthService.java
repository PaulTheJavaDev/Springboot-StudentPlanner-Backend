package de.pls.stundenplaner.service;

import de.pls.stundenplaner.dto.LoginRequest;
import de.pls.stundenplaner.dto.RegisterRequest;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.repository.UserRepository;
import de.pls.stundenplaner.util.PasswordHasher;
import de.pls.stundenplaner.util.exceptions.InvalidLoginException;
import de.pls.stundenplaner.util.exceptions.UserAlreadyExistsException;
import io.micrometer.common.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * Throws {@link de.pls.stundenplaner.util.exceptions.InvalidLoginException} if username doesn't exist or password is wrong.
     */
    public boolean checkLogin(
            @NonNull LoginRequest loginRequest
    ) {

        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            throw new InvalidLoginException();
        }

        User user = optionalUser.get();
        String hashedPassword = PasswordHasher.sha256(loginRequest.getPassword());

        return user.getPassword_hash().equals(hashedPassword);

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