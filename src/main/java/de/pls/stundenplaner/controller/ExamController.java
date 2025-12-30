package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.model.Exam;
import de.pls.stundenplaner.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exams/my")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> findExamsByUserUUID(
            final @RequestHeader("SessionID") UUID sessionID
    ) {

        return examService.getAllExams(sessionID);

    }

    @GetMapping("/{examId}")
    public ResponseEntity<Exam> findExamById(
            final @PathVariable int examId
    ) {
        return examService.getExam(examId);
    }

    @PostMapping
    public ResponseEntity<Exam> addExam(
            final @RequestBody Exam examBody
    ) {
        return examService.createExam(examBody);
    }

    @PutMapping("/{examId}")
    public ResponseEntity<Exam> updateExam(
            final @PathVariable int examId,
            final @Valid @RequestBody Exam examBody
    ) {
        return examService.updateExam(examId, examBody);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(
            final @PathVariable int examId
    ) {
        return examService.deleteExam(examId);
    }
}
