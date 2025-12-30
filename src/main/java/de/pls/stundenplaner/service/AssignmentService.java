package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.repository.AssignmentRepository;
import de.pls.stundenplaner.repository.UserRepository;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
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
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Gets all Assignments saved from a User in the Database
     */
    public ResponseEntity<List<Assignment>> getAssignments(
            @NotNull final UUID sessionID
    ) {

        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        Optional<List<Assignment>> assignments = assignmentRepository.findAssignmentsByStudentUUID(user.getUserUUID());

        return assignments.map(
                assignmentList -> new ResponseEntity<>(assignmentList, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Get a single Assignment from a User
     */
    public ResponseEntity<Assignment> getAssignment(
            final int assignmentId
    ) {

        Optional<Assignment> searchedAssignment = assignmentRepository.findById(assignmentId);

        return searchedAssignment.map(
                assignment -> new ResponseEntity<>(assignment, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Creates an Assignment
     */
    public ResponseEntity<Assignment> createAssignment(
            @NotNull final UUID sessionID,
            @NotNull final Assignment assignment
    ) {

        User user = userRepository.findBySessionID(sessionID)
                        .orElseThrow(InvalidSessionException::new);

        assignment.setUserUUID(user.getUserUUID());

        return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.CREATED);
    }

    /**
     * Updates an Assignment
     *
     * @param updated Container of the To-Update-Information for the found Assignment
     */
    public ResponseEntity<Assignment> updateAssignment(
            final int id,
            @NotNull final Assignment updated
    ) {

        final Optional<Assignment> assignmentToUpdate = assignmentRepository.findById(id);

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
            final int assignmentId
    ) {
        final Optional<Assignment> assignmentToDelete = assignmentRepository.findById(assignmentId);

        if (assignmentToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        assignmentRepository.delete(assignmentToDelete.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
