package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.dto.request.exam.CreateExamRequest;
import de.pls.stundenplaner.dto.request.exam.UpdateExamRequest;
import de.pls.stundenplaner.service.ExamService;
import de.pls.stundenplaner.model.ExamEntity;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ExamEntity>> getExams(
            final @RequestHeader("SessionID") UUID sessionID
    ) {

        List<ExamEntity> exams = examService.getAllExams(sessionID);
        return ResponseEntity.ok(exams);

    }

    @PostMapping
    public ResponseEntity<ExamEntity> addExam(
            final @RequestHeader("SessionID") UUID sessionID,
            @RequestBody CreateExamRequest createExamRequest
    ) {
        ExamEntity exam = examService.createExam(sessionID, createExamRequest);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @PutMapping("/{examId}")
    public ResponseEntity<ExamEntity> updateExam(
            final @RequestHeader("SessionID") UUID sessionID,
            final @PathVariable int examId,
            @RequestBody UpdateExamRequest updateExamRequest
    ) {
        ExamEntity exam = examService.updateExam(sessionID, examId, updateExamRequest);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(
            final @RequestHeader("SessionID") UUID sessionID,
            final @PathVariable int examId
    ) {
        examService.deleteExam(sessionID, examId);
        return ResponseEntity.ok().build();
    }
}
