/*

package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.model.Subject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class AssignmentServiceTests {

    private MongoAssignmentRepository mongoAssignmentRepository;
    private AssignmentService assignmentService;

    @BeforeEach
    void setup() {

        mongoAssignmentRepository = mock(MongoAssignmentRepository.class);
        assignmentService = new AssignmentService(mongoAssignmentRepository);

    }

    @Test
    void testGetAllAssignmentsForUser() {

        String studentUUID = UUID.randomUUID().toString();

        Assignment assignment1 = new Assignment(new ObjectId(), studentUUID, Subject.GERMAN, new Date());
        Assignment assignment2 = new Assignment(new ObjectId(), studentUUID, Subject.MATH, new Date());
        List<Assignment> mockAssignmentList = List.of(assignment1, assignment2);

        when(mongoAssignmentRepository.findByIdentifier(studentUUID)).thenReturn(mockAssignmentList);

        ResponseEntity<List<Assignment>> response = assignmentService.getAllAssignmentsForUser(studentUUID);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(mockAssignmentList, response.getBody());

        verify(mongoAssignmentRepository, times(1)).findByIdentifier(studentUUID);

    }

    @Test
    void testGetAssignmentFound() {

        final String studentUUID = UUID.randomUUID().toString();
        final ObjectId id = new ObjectId();
        final Assignment assignment = new Assignment();
        assignment.setId(id);

        when(mongoAssignmentRepository.findAssignmentByIdentifierAndId(studentUUID, id)).thenReturn(assignment);

        ResponseEntity<Assignment> response = assignmentService.getAssignment(studentUUID, id);

        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assignment, response.getBody());

    }

    @Test
    void testGetAssignmentNotFound() {

        final String studentUUID = UUID.randomUUID().toString();
        final ObjectId objectId = new ObjectId();

        when(mongoAssignmentRepository.findAssignmentByIdentifierAndId(studentUUID, objectId)).thenReturn(null);

        ResponseEntity<Assignment> response = assignmentService.getAssignment(studentUUID, objectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

}


 */