package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.service.AssignmentService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/students/{studentUUID}/assignments")
public abstract class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignmentsForUser(
            @PathVariable String studentUUID
    ) {

        return service.getAllAssignmentsForUser(studentUUID);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(
            @PathVariable String studentUUID,
            @PathVariable ObjectId id
    ) {

        return service.getAssignment(studentUUID, id);

    }

    @PostMapping
    public ResponseEntity<Assignment> create(
            @PathVariable String studentUUID,
            @Valid @RequestBody Assignment assignment
    ) {

        return service.createAssignment(studentUUID, assignment);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> update(
            @PathVariable String studentUUID,
            @PathVariable String id,
            @Valid @RequestBody Assignment updated
    ) {

        return service.updateAssignment(studentUUID, new ObjectId(id), updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable String studentUUID,
            @PathVariable String id
    ) {

        return service.deleteAssignment(studentUUID, new ObjectId(id));

    }
}
