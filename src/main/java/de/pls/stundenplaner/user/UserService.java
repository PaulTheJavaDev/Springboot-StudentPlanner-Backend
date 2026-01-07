package de.pls.stundenplaner.user;

import de.pls.stundenplaner.auth.dto.request.ChangePasswordRequest;
import de.pls.stundenplaner.scheduler.SchedulerRepository;
import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import de.pls.stundenplaner.scheduler.model.TimeStamp;
import de.pls.stundenplaner.util.exceptions.auth.InvalidSessionException;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.util.PasswordHasher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            final @NotNull @Valid User user
    ) {

        if (
                userRepository.existsById(user.getId()) ||
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

    public ResponseEntity<User> getUser(final UUID sessionID) {

        Optional<User> user = userRepository.findBySessionID(sessionID);

        return user.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<Void> deleteUser(final UUID sessionId) {

        if (userRepository.findBySessionID(sessionId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteBySessionID(sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
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
