package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/assignments/my")
public class AssignmentController {

    private final AssignmentService service;

    protected AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getMyAssignments(
            @RequestHeader(name = "SessionID") UUID sessionID
    ) {

        return service.getAssignments(sessionID);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(
            @PathVariable int id
    ) {

        return service.getAssignment(id);

    }

    @PostMapping
    public ResponseEntity<Assignment> create(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @Valid @RequestBody Assignment assignment
    ) {

        return service.createAssignment(sessionID, assignment);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> update(
            @PathVariable int id,
            @Valid @RequestBody Assignment updated
    ) {

        return service.updateAssignment(id, updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable int id
    ) {

        return service.deleteAssignment(id);

    }
}
