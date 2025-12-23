package de.pls.stundenplaner.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class UserTests {

    @Test
    void testUserGettersAndSetters() {

        final UUID userUUID = UUID.randomUUID();
        final String username = "username";
        final String password_hash = "password";

        final User user = new User();
        user.setUsername(username);
        user.setPassword_hash(password_hash);
        user.setUserUUID(userUUID);

        assertEquals(username, user.getUsername());
        assertEquals(password_hash, user.getPassword_hash());
        assertEquals(userUUID, user.getUserUUID());

    }

    @Test
    void testUserGettersAndSetters_null() {

        final User user = new User();
        assertNull(user.getUserUUID());
        assertNull(user.getUsername());
        assertNull(user.getPassword_hash());

    }

}
