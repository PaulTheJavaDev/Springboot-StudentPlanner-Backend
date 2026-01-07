package de.pls.stundenplaner.auth;

import de.pls.stundenplaner.auth.dto.request.LoginRequest;
import de.pls.stundenplaner.auth.dto.request.RegisterRequest;
import de.pls.stundenplaner.scheduler.SchedulerRepository;
import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.util.PasswordHasher;
import de.pls.stundenplaner.util.exceptions.auth.InvalidLoginException;
import de.pls.stundenplaner.util.exceptions.auth.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;
import java.util.UUID;

/**
 * Handles the Authentication process.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SchedulerRepository schedulerRepository;

    public AuthService(UserRepository userRepository, SchedulerRepository schedulerRepository) {
        this.userRepository = userRepository;
        this.schedulerRepository = schedulerRepository;
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

        initializeScheduleDays(userForRegister.getUserUUID());
    }

    void initializeScheduleDays(final UUID userUUID) {

        // Iteriere über alle Wochentage im Enum
        for (DayOfWeek day : DayOfWeek.values()) {
            ScheduleDay scheduleDay = new ScheduleDay(day, userUUID);
            schedulerRepository.save(scheduleDay);
        }
    }

}