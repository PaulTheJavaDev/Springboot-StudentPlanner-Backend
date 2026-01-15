package de.pls.stundenplaner.assignment;

import de.pls.stundenplaner.assignment.model.Assignment;
import de.pls.stundenplaner.assignment.model.sorting.SortCondition;
import de.pls.stundenplaner.assignment.model.sorting.SortDirection;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<Assignment>> getMyAssignments(
            @RequestHeader(name = "SessionID") UUID sessionID
    ) {

        return service.getAssignments(sessionID);

    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @PathVariable int assignmentId
    ) {

        return service.getAssignment(sessionID, assignmentId);

    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @Valid @RequestBody Assignment assignmentBody
    ) {

        return service.createAssignment(sessionID, assignmentBody);

    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> update(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable int assignmentId,
            @Valid @RequestBody Assignment updated
    ) {

        return service.updateAssignment(sessionID, assignmentId, updated);

    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @RequestHeader(name = "SessionID") UUID sessionID,
            @PathVariable int assignmentId
    ) {

        return service.deleteAssignment(sessionID, assignmentId);

    }
}
