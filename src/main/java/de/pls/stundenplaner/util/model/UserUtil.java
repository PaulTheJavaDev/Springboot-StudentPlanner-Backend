package de.pls.stundenplaner.util.model;

import de.pls.stundenplaner.repository.UserRepository;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserUtil {

    private static UserRepository userRepository;

    public UserUtil(UserRepository userRepository) {
        UserUtil.userRepository = userRepository;
    }

    public static User checkUserExistenceBySessionID(
            final @NotNull UUID sessionID
    ) {

        return userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

    }

}
