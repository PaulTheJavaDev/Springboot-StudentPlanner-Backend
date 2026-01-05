package de.pls.stundenplaner.assignment;

import de.pls.stundenplaner.assignment.model.Assignment;
import de.pls.stundenplaner.assignment.model.sorting.SortCondition;
import de.pls.stundenplaner.assignment.model.sorting.SortDirection;
import de.pls.stundenplaner.auth.exceptions.InvalidSessionException;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.user.model.User;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public final class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<Assignment>> getAssignments(
            @NonNull UUID sessionID
    ) {

        User user = validateSession(sessionID);

        Optional<List<Assignment>> assignments = assignmentRepository.findAssignmentsByStudentUUID(user.getUserUUID());

        return assignments.map(
                assignmentList -> new ResponseEntity<>(assignmentList, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<Assignment> getAssignment(
            @NonNull UUID sessionID,
            final int assignmentId
    ) {
        validateSession(sessionID);

        Optional<Assignment> searchedAssignment = assignmentRepository.findById(assignmentId);

        return searchedAssignment.map(
                assignment -> new ResponseEntity<>(assignment, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    // NOTE: Don't forget or get confused by the different UUIDs. The sessionID is parsed to get the userUUID (a different one than the sessionID)
    public ResponseEntity<Assignment> createAssignment(
            @NonNull UUID sessionID,
            @NonNull @Valid Assignment assignment
    ) {

        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        assignment.setUserUUID(user.getUserUUID());

        return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.CREATED);
    }

    public ResponseEntity<Assignment> updateAssignment(
            @NonNull UUID sessionID,
            final int id,
            @NonNull @Valid Assignment updated
    ) {

        User user = validateSession(sessionID);

        final Optional<Assignment> assignmentToUpdate = assignmentRepository.findById(id);

        if (assignmentToUpdate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Assignment assignment = assignmentToUpdate.get();
        assignment.setSubject(updated.getSubject());
        assignment.setDueDate(updated.getDueDate());
        assignment.setCompleted(updated.isCompleted());
        assignment.setUserUUID(user.getUserUUID());

        return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAssignment(
            final UUID sessionID,
            final int assignmentId
    ) {
        validateSession(sessionID);

        final Optional<Assignment> assignmentToDelete = assignmentRepository.findById(assignmentId);

        if (assignmentToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        assignmentRepository.delete(assignmentToDelete.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User validateSession(UUID sessionID) {
        return userRepository.findBySessionID(sessionID).orElseThrow(InvalidSessionException::new);
    }

    private List<Assignment> sortAssignments(
            List<Assignment> assignments,
            SortCondition sortCondition,
            SortDirection sortDirection
    ) {

        switch (sortCondition) {

            case SortCondition.COMPLETED -> {
                return assignments
                        .stream()
                        .sorted(
                                Comparator.comparing(Assignment::isCompleted)
                        ).toList();
            }
            case SortCondition.DUE_DATE -> {
                return assignments
                        .stream()
                        .sorted(
                                Comparator.comparing(Assignment::getDueDate)
                        ).toList();
            }
            case SortCondition.SUBJECT -> {
                return assignments
                        .stream()
                        .sorted(
                                Comparator.comparing(Assignment::getSubject)
                        ).toList();
            }
            default -> {
                return assignments;
            }

        }

    }
}
