package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
