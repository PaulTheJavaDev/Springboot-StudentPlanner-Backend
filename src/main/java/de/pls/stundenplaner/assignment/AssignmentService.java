package de.pls.stundenplaner.assignment;

import de.pls.stundenplaner.assignment.model.Assignment;
import de.pls.stundenplaner.assignment.model.sorting.SortCondition;
import de.pls.stundenplaner.assignment.model.sorting.SortDirection;
import de.pls.stundenplaner.user.model.User;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.pls.stundenplaner.util.model.UserUtil.checkUserExistenceBySessionID;

@Service
public final class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public ResponseEntity<List<Assignment>> getAssignments(
            final @NotNull UUID sessionID
    ) {

        User user = validateSession(sessionID);

        Optional<List<Assignment>> assignments = assignmentRepository.findAssignmentsByStudentUUID(user.getUserUUID());

        return assignments.map(
                assignmentList -> new ResponseEntity<>(assignmentList, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<Assignment> getAssignment(
            final @NotNull UUID sessionID,
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
            @NotNull UUID sessionID,
            @NotNull @Valid Assignment assignment
    ) {

        User user = checkUserExistenceBySessionID(sessionID);

        assignment.setUserUUID(user.getUserUUID());

        return new ResponseEntity<>(assignmentRepository.save(assignment), HttpStatus.CREATED);
    }

    public ResponseEntity<Assignment> updateAssignment(
            @NotNull UUID sessionID,
            final int id,
            @NotNull @Valid Assignment updated
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

    private User validateSession(
            final @NotNull UUID sessionID
    ) {
        return checkUserExistenceBySessionID(sessionID);
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
