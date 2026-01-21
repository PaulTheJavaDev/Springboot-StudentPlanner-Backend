package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.dto.request.assignment.CreateAssignmentRequest;
import de.pls.stundenplaner.dto.request.assignment.UpdateAssignmentRequest;
import de.pls.stundenplaner.model.AssignmentEntity;
import de.pls.stundenplaner.service.AssignmentService;
import org.springframework.http.HttpStatus;
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

    // TODO: add ascending and descending
    @GetMapping
    public ResponseEntity<List<AssignmentEntity>> getMyAssignments(
            @RequestHeader(name = "SessionID") UUID sessionID
    ) {
        List<AssignmentEntity> assignmentEntityList = service.getAssignments(sessionID);
        return ResponseEntity.ok(assignmentEntityList);
    }

    @PostMapping
    public ResponseEntity<AssignmentEntity> createAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @RequestBody CreateAssignmentRequest createRequest
    ) {

        return new ResponseEntity<>(service.createAssignment(sessionID, createRequest), HttpStatus.CREATED);

    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentEntity> update(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable int assignmentId,
            @RequestBody UpdateAssignmentRequest updateRequest
    ) {

        return new ResponseEntity<>(service.updateAssignment(sessionID, assignmentId, updateRequest), HttpStatus.OK);

    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @PathVariable int assignmentId
    ) {

        service.deleteAssignment(sessionID, assignmentId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
