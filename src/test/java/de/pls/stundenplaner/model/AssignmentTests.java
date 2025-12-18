package de.pls.stundenplaner.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

final class AssignmentTests {

    @Test
    void testAssignmentGettersAndSetters() {

        ObjectId objectId = new ObjectId();
        String identifier = "Test";
        Subject subject = Subject.GERMAN;
        Date dueDate = new Date();

        Assignment assignment = new Assignment();
        assignment.setId(objectId);
        assignment.setSubject(subject);
        assignment.setIdentifier(identifier);
        assignment.setDueDate(dueDate);

        assertEquals(objectId, assignment.getId());
        assertEquals(identifier, assignment.getIdentifier());
        assertEquals(subject, assignment.getSubject());
        assertEquals(dueDate, assignment.getDueDate());

    }

}
