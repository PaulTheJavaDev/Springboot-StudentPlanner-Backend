package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.repository.MongoAssignmentRepository;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class AssignmentService {

    private final MongoAssignmentRepository mongoAssignmentRepository;

    public AssignmentService(MongoAssignmentRepository repo) {
        this.mongoAssignmentRepository = repo;
    }

    public ResponseEntity<List<Assignment>> getAllAssignmentsForUser(
            @NotNull final String studentUUID
    ) {

        List<Assignment> assignments = mongoAssignmentRepository.findByIdentifier(studentUUID);
        return ResponseEntity.ok(assignments);

    }

    public ResponseEntity<Assignment> getAssignment(
            @NotNull final String studentUUID,
            @NotNull final ObjectId id
    ) {

        Assignment searchedAssignment = mongoAssignmentRepository.findAssignmentByIdentifierAndId(studentUUID, id);

        if (searchedAssignment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(searchedAssignment);

    }

    public ResponseEntity<Assignment> createAssignment(
            @NotNull final String studentUUID,
            @NotNull final Assignment assignment
    ) {
        assignment.setId(new ObjectId());
        assignment.setIdentifier(studentUUID);

        return new ResponseEntity<>(mongoAssignmentRepository.save(assignment), HttpStatus.CREATED);
    }

    public ResponseEntity<Assignment> updateAssignment(
            @NotNull final String studentUUID,
            @NotNull final ObjectId id,
            @NotNull final Assignment updated
    ) {
        final Assignment assignmentToUpdate = mongoAssignmentRepository.findAssignmentByIdentifierAndId(studentUUID, id);

        if (assignmentToUpdate == null) {
            return ResponseEntity.notFound().build();
        }

        assignmentToUpdate.setSubject(updated.getSubject());
        assignmentToUpdate.setDueDate(updated.getDueDate());

        return ResponseEntity.ok(mongoAssignmentRepository.save(assignmentToUpdate));
    }

    public ResponseEntity<Void> deleteAssignment(
            @NotNull final String studentUUID,
            @NotNull final ObjectId id
    ) {
        Assignment assignmentToDelete = mongoAssignmentRepository.findAssignmentByIdentifierAndId(studentUUID, id);

        if (assignmentToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        mongoAssignmentRepository.delete(assignmentToDelete);

        return ResponseEntity.noContent().build();
    }
}
