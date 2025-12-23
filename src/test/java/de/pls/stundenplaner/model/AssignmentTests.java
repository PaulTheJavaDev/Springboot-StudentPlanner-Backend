package de.pls.stundenplaner.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AssignmentTests {

    @Test
    void testAssignmentGettersAndSetters() {

        int id = 0;
        String identifier = "Test";
        Subject subject = Subject.GERMAN;
//        LocalDate dueDate = new LocalDate();

        Assignment assignment = new Assignment();
        assignment.setId(id);
        assignment.setSubject(subject);
        assignment.setStudentUUID(identifier);
        // assignment.setDueDate(dueDate);

        assertEquals(id, assignment.getId());
        assertEquals(identifier, assignment.getStudentUUID());
        assertEquals(subject, assignment.getSubject());
        // assertEquals(dueDate, assignment.getDueDate());

    }

}
