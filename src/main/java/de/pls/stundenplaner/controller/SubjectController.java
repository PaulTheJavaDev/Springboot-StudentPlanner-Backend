package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Subject;
import de.pls.stundenplaner.service.AssignmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Handles Web Requests for the Subjects
 */
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    /**
     * Gets all possible Subjects
     * @return A List of Subjects
     */
    @GetMapping
    public List<String> getAllSubjects() {
        return Subject.getAllSubjects()
                .stream()
                .map(Subject::getName)
                .toList();
    }

}