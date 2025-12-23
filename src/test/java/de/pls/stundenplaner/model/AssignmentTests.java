package de.pls.stundenplaner.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class AssignmentTests {

    @Test
    void testAssignmentGettersAndSetters() {

        UUID userUUID = UUID.randomUUID();
        Subject subject = Subject.GERMAN;
        LocalDate dueDate = LocalDate.now();

        Assignment assignment = new Assignment();
        assignment.setSubject(subject);
        assignment.setUserUUID(userUUID);
        assignment.setDueDate(dueDate);

        assertEquals(userUUID, assignment.getUserUUID());
        assertEquals(subject, assignment.getSubject());
        assertEquals(dueDate, assignment.getDueDate());

    }

}
