package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/students/{userUUID}/assignments")
public class AssignmentController {

    private final AssignmentService service;

    protected AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignmentsForUser(
            @PathVariable UUID userUUID
    ) {

        return service.getAllAssignmentsForUser(userUUID);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(
            @PathVariable UUID userUUID,
            @PathVariable int id
    ) {

        return service.getAssignment(userUUID, id);

    }

    @PostMapping
    public ResponseEntity<Assignment> create(
            @PathVariable UUID userUUID,
            @Valid @RequestBody Assignment assignment
    ) {

        return service.createAssignment(userUUID, assignment);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> update(
            @PathVariable UUID userUUID,
            @PathVariable int id,
            @Valid @RequestBody Assignment updated
    ) {

        return service.updateAssignment(userUUID, id, updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable UUID userUUID,
            @PathVariable int id
    ) {

        return service.deleteAssignment(userUUID, id);

    }
}
