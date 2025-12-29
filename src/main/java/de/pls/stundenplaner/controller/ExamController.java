package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Exam;
import de.pls.stundenplaner.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exams/{userUUID}")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> findExamsByUserUUID(final @PathVariable UUID userUUID) {
        return examService.getAllExams(userUUID);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> findExamById(
            final @PathVariable UUID userUUID,
            final @PathVariable int id
    ) {
        return examService.getExam(userUUID, id);
    }

    @PostMapping
    public ResponseEntity<Exam> addExam(
            final @PathVariable UUID userUUID,
            @RequestBody Exam exam
    ) {
        return examService.createExam(userUUID, exam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(
            final @PathVariable UUID userUUID,
            final @PathVariable int id,
            final @Valid @RequestBody Exam exam
    ) {
        return examService.updateExam(userUUID, id, exam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(
            @PathVariable UUID userUUID,
            @PathVariable int id
    ) {
        return examService.deleteExam(userUUID, id);
    }
}
