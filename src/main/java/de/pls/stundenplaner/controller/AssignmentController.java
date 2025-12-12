package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students/{studentUUID}/assignments")
public class AssignmentController {

    final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // 123e4567-e89b-12d3-a456-426614174000
    @GetMapping
    public List<Assignment> getMyAssignments(@PathVariable UUID studentUUID) {
        return assignmentService.getAllAssignments(studentUUID);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(
            @PathVariable UUID studentUUID,
            @PathVariable int id
    ) {
        return assignmentService.getAssignment(studentUUID, id);
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(
            @PathVariable UUID studentUUID,
            @RequestBody Assignment assignmentToCreate
    ) {

        return assignmentService.createAssignment(studentUUID, assignmentToCreate);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable UUID studentUUID,
            @PathVariable int id,
            @RequestBody Assignment assignmentToUpdate
    ) {

        return assignmentService.updateAssignment(studentUUID, id, assignmentToUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(
            @PathVariable UUID studentUUID,
            @PathVariable int id
    ) {
        return assignmentService.deleteAssignment(studentUUID, id);
    }
}
