package de.pls.stundenplaner.user;

import de.pls.stundenplaner.auth.dto.request.ChangePasswordRequest;
import de.pls.stundenplaner.auth.exceptions.InvalidSessionException;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.util.PasswordHasher;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> createUser(
            final @NotNull User user
    ) {

        if (
                checkUserExistenceById(user.getId()) ||
                        user.getUsername() == null ||
                        user.getUsername().isEmpty() ||
                        user.getPassword_hash() == null ||
                        user.getPassword_hash().isEmpty()
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    public ResponseEntity<List<User>> getUsers() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(final int id) {

        Optional<User> user = userRepository.findById(id);

        return user.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<Void> deleteUser(final int id) {

        if (!checkUserExistenceById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean checkUserExistenceById(final int id) {
        return userRepository.existsById(id);
    }

    public ResponseEntity<User> changePassword(
            UUID sessionID,
            ChangePasswordRequest request
    ) {
        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        String oldPasswordHash =
                PasswordHasher.sha256(request.getPassword());

        if (!oldPasswordHash.equals(user.getPassword_hash())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword_hash(
                PasswordHasher.sha256(request.getNewPassword())
        );

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
