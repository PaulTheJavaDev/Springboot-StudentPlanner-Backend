package de.pls.stundenplaner.exam;

import de.pls.stundenplaner.exam.model.Exam;
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
            final @RequestHeader("SessionID") UUID sessionID,
            final @PathVariable int examId
    ) {
        return examService.getExam(sessionID, examId);
    }

    @PostMapping
    public ResponseEntity<Exam> addExam(
            final @RequestHeader("SessionID") UUID sessionID,
            final @RequestBody Exam examBody
    ) {
        return examService.createExam(sessionID, examBody);
    }

    @PutMapping("/{examId}")
    public ResponseEntity<Exam> updateExam(
            final @RequestHeader("SessionID") UUID sessionID,
            final @PathVariable int examId,
            final @Valid @RequestBody Exam examBody
    ) {
        return examService.updateExam(sessionID, examId, examBody);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(
            final @RequestHeader("SessionID") UUID sessionID,
            final @PathVariable int examId
    ) {
        return examService.deleteExam(sessionID, examId);
    }
}
