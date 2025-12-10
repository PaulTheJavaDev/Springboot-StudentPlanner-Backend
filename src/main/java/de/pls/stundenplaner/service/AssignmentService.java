package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.repository.AssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static de.pls.stundenplaner.util.ControllerMessages.HttpStatusMessages.*;

@Service
public class AssignmentService {

    final AssignmentRepository assignmentRepository;

    /**
     * Constructor for creation of the {@link AssignmentRepository}
     * @param assignmentRepository temporary Repository of the Assignment Model.<br>
     *                             look: {@link Assignment}
     */
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Gets all Assignments for one user based on the StudentUUID.<br>
     * The StudentUUID is already in the URL.
     * @param studentUUID Credential for searching in all the Repository's contents.
     * @return all found Assignments
     */
    public List<Assignment> getAllAssignments(UUID studentUUID) {
        return assignmentRepository.findByStudentUUID(studentUUID);
    }

    public ResponseEntity<String> deleteAssignment(final int id) {

        if (assignmentRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(assignment_To_Be_Deleted_Not_Found_Message, HttpStatus.NOT_FOUND);
        }

        try {
            assignmentRepository.deleteById(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(success_Full_assignment_Deletion_Message, HttpStatus.OK);
    }

    /**
     * Gets a Singular Assignments from the current User based on the StudentUUID.
     * @param studentUUID Credential for searching the {@link AssignmentRepository}.
     * @param id Second credential for searching.
     * @return The proper HTTPStatus
     */
    public ResponseEntity<Assignment> getAssignment(
            final UUID studentUUID,
            final int id
    ) {

        Assignment assignment = assignmentRepository.findAssignmentByStudentUUIDAndId(studentUUID, id);

        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(assignment);

    }

    /**
     *
     * @param studentUUID
     * @param assignmentToCreate
     * @return
     */
    public ResponseEntity<Assignment> createAssignment(
            final UUID studentUUID,
            final Assignment assignmentToCreate
    ) {

        assignmentToCreate.setId(0);
        assignmentToCreate.setStudentUUID(studentUUID);

        Assignment savedAssignment = assignmentRepository.save(assignmentToCreate);
        return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);

    }

    public ResponseEntity<Assignment> updateAssignment(
            UUID studentUUID,
            int id,
            Assignment updatedData
    ) {

        Assignment assignment =
                assignmentRepository.findAssignmentByStudentUUIDAndId(studentUUID, id);

        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }

        assignment.setSubject(updatedData.getSubject());
        assignment.setDueDate(updatedData.getDueDate());

        return ResponseEntity.ok(assignmentRepository.save(assignment));

    }
}