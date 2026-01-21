package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @GetMapping
    public List<String> getAllSubjects() {
        return Subject.getAllSubjects()
                .stream()
                .map(Subject::getName)
                .toList();
    }

}