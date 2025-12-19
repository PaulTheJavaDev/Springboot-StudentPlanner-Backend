package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.repository.AssignmentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class AssignmentService {

    private final AssignmentRepository AssignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.AssignmentRepository = assignmentRepository;
    }

    public ResponseEntity<List<Assignment>> getAllAssignmentsForUser(
            @NotNull final String studentUUID
    ) {

        List<Assignment> assignments = AssignmentRepository.findAssignmentsByStudentUUID(studentUUID);
        return ResponseEntity.ok(assignments);

    }

    public ResponseEntity<Assignment> getAssignment(
            @NotNull final String studentUUID,
            final int id
    ) {

        Assignment searchedAssignment = AssignmentRepository.findAssignmentByStudentUUIDAndId(studentUUID, id);

        if (searchedAssignment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(searchedAssignment);

    }

    public ResponseEntity<Assignment> createAssignment(
            @NotNull final String studentUUID,
            @NotNull final Assignment assignment
    ) {
        assignment.setId(0);
        assignment.setStudentUUID(studentUUID);

        return new ResponseEntity<>(AssignmentRepository.save(assignment), HttpStatus.CREATED);
    }

    public ResponseEntity<Assignment> updateAssignment(
            @NotNull final String studentUUID,
            final int id,
            @NotNull final Assignment updated
    ) {
        final Assignment assignmentToUpdate = AssignmentRepository.findAssignmentByStudentUUIDAndId(studentUUID, id);

        if (assignmentToUpdate == null) {
            return ResponseEntity.notFound().build();
        }

        assignmentToUpdate.setSubject(updated.getSubject());
        assignmentToUpdate.setDueDate(updated.getDueDate());

        return ResponseEntity.ok(AssignmentRepository.save(assignmentToUpdate));
    }

    public ResponseEntity<Void> deleteAssignment(
            @NotNull final String studentUUID,
            final int id
    ) {
        Assignment assignmentToDelete = AssignmentRepository.findAssignmentByStudentUUIDAndId(studentUUID, id);

        if (assignmentToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        AssignmentRepository.delete(assignmentToDelete);

        return ResponseEntity.noContent().build();
    }
}
