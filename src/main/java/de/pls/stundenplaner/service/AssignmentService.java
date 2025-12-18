package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.repository.MongoAssignmentRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final MongoAssignmentRepository repo;

    public AssignmentService(MongoAssignmentRepository repo) {
        this.repo = repo;
    }

    public List<Assignment> getAllAssignments(String studentUUID) {
        return repo.findByIdentifier(studentUUID);
    }

    public ResponseEntity<Assignment> getAssignment(String studentUUID, ObjectId id) {

        Assignment assignment = repo.findAssignmentByIdentifierAndId(studentUUID, id);

        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assignment);
    }

    public ResponseEntity<Assignment> createAssignment(String studentUUID, Assignment assignment) {
        assignment.setId(new ObjectId());
        assignment.setIdentifier(studentUUID);
        return new ResponseEntity<>(repo.save(assignment), HttpStatus.CREATED);
    }

    public ResponseEntity<Assignment> updateAssignment(String studentUUID, ObjectId id, Assignment updated) {
        Assignment assignment = repo.findAssignmentByIdentifierAndId(studentUUID, id);
        if (assignment == null) return ResponseEntity.notFound().build();
        assignment.setSubject(updated.getSubject());
        assignment.setDueDate(updated.getDueDate());
        return ResponseEntity.ok(repo.save(assignment));
    }

    public ResponseEntity<Void> deleteAssignment(String studentUUID, ObjectId id) {
        Assignment assignment = repo.findAssignmentByIdentifierAndId(studentUUID, id);
        if (assignment == null) return ResponseEntity.notFound().build();
        repo.delete(assignment);
        return ResponseEntity.noContent().build();
    }
}
