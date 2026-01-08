package de.pls.stundenplaner.subject;

import de.pls.stundenplaner.subject.model.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

//    private final SubjectService subjectService;
//
//    public SubjectController(SubjectService subjectService) {
//        this.subjectService = subjectService;
//    }

    @GetMapping
    public List<String> getAllSubjects() {
        return Subject.getAllSubjects()
                .stream()
                .map(Subject::getName)
                .toList();
    }

}