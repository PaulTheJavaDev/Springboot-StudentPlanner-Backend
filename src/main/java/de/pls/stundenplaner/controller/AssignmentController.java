package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Assignment;
import de.pls.stundenplaner.service.AssignmentService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students/{studentUUID}/assignments")
public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Assignment> getAll(@PathVariable String studentUUID) {
        return service.getAllAssignments(studentUUID);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getOne(@PathVariable String studentUUID, @PathVariable String id) {
        return service.getAssignment(studentUUID, new ObjectId(id));
    }

    @PostMapping
    public ResponseEntity<Assignment> create(@PathVariable String studentUUID, @RequestBody Assignment assignment) {
        return service.createAssignment(studentUUID, assignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> update(@PathVariable String studentUUID, @PathVariable String id,
                                             @RequestBody Assignment updated) {
        return service.updateAssignment(studentUUID, new ObjectId(id), updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String studentUUID, @PathVariable String id) {
        return service.deleteAssignment(studentUUID, new ObjectId(id));
    }
}
