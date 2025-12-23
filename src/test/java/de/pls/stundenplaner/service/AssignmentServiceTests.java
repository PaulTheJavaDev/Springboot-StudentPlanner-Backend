
package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.model.Subject;
import de.pls.stundenplaner.repository.AssignmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class AssignmentServiceTests {

    private AssignmentRepository assignmentRepository;
    private AssignmentService assignmentService;

    @BeforeEach
    void setup() {
        assignmentRepository = mock(AssignmentRepository.class);
        assignmentService = new AssignmentService(assignmentRepository);
    }

    @Test
    void testGetAllAssignmentsForUser() {

        UUID userUUID = UUID.randomUUID();

        Assignment assignment1 = new Assignment(userUUID, Subject.GERMAN, LocalDate.now());
        Assignment assignment2 = new Assignment(userUUID, Subject.MATH, LocalDate.now());
        List<Assignment> mockAssignmentList = List.of(assignment1, assignment2);

        when(assignmentRepository.findAssignmentsByStudentUUID(userUUID)).thenReturn(Optional.of(mockAssignmentList));

        ResponseEntity<List<Assignment>> response = assignmentService.getAllAssignmentsForUser(userUUID);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(mockAssignmentList, response.getBody());

        verify(assignmentRepository, times(1)).findAssignmentsByStudentUUID(userUUID);

    }

}