package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.dto.request.assignment.CreateAssignmentRequest;
import de.pls.stundenplaner.dto.request.assignment.UpdateAssignmentRequest;
import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.service.AssignmentService;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
import de.pls.stundenplaner.util.exceptions.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Handles Web Requests for the Assignments via {@link AssignmentService}
 */
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
    ) throws InvalidSessionException {

        List<Assignment> assignmentList = service.getAssignments(sessionID);
        return ResponseEntity.ok(assignmentList);

    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @RequestBody CreateAssignmentRequest createRequest
    ) throws InvalidSessionException {

        return new ResponseEntity<>(service.createAssignment(sessionID, createRequest), HttpStatus.CREATED);

    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> update(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable int assignmentId,
            @RequestBody UpdateAssignmentRequest updateRequest
    ) throws UnauthorizedAccessException, InvalidSessionException {

        return new ResponseEntity<>(service.updateAssignment(sessionID, updateRequest, assignmentId), HttpStatus.OK);

    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @PathVariable int assignmentId
    ) throws InvalidSessionException {

        service.deleteAssignment(sessionID, assignmentId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
