package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.repository.AssignmentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer of the {@link Assignment} class
 */
@Service
public final class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Gets all Assignments saved from a User in the Database
     */
    public ResponseEntity<List<Assignment>> getAllAssignmentsForUser(
            @NotNull final UUID userUUID
    ) {

        Optional<List<Assignment>> assignments = assignmentRepository.findAssignmentsByStudentUUID(userUUID);

        return assignments.map(
                assignmentList -> new ResponseEntity<>(assignmentList, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Get a single Assignment from a User
     */
    public ResponseEntity<Assignment> getAssignment(
            @NotNull final UUID userUUID,
            final int assignmentId
    ) {

        Optional<Assignment> searchedAssignment = assignmentRepository.findAssignmentByUserUUIDAndId(userUUID, assignmentId);

        return searchedAssignment.map(
                assignment -> new ResponseEntity<>(assignment, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Creates an Assignment
     */
    public ResponseEntity<Assignment> createAssignment(
            @NotNull final UUID userUUID,
            @NotNull final Assignment assignment
    ) {
        assignment.setUserUUID(userUUID);

        return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.CREATED);
    }

    /**
     * Updates an Assignment
     *
     * @param updated Container of the To-Update-Information for the found Assignment
     */
    public ResponseEntity<Assignment> updateAssignment(
            @NotNull final UUID userUUID,
            final int id,
            @NotNull final Assignment updated
    ) {
        final Optional<Assignment> assignmentToUpdate = assignmentRepository.findAssignmentByUserUUIDAndId(userUUID, id);

        if (assignmentToUpdate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Assignment assignment = assignmentToUpdate.get();
        assignment.setSubject(updated.getSubject());
        assignment.setDueDate(updated.getDueDate());

        return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.OK);
    }

    /**
     * Deletes an Assignment
     */
    public ResponseEntity<Void> deleteAssignment(
            @NotNull final UUID studentUUID,
            final int id
    ) {
        final Optional<Assignment> assignmentToDelete = assignmentRepository.findAssignmentByUserUUIDAndId(studentUUID, id);

        if (assignmentToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        assignmentRepository.delete(assignmentToDelete.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
