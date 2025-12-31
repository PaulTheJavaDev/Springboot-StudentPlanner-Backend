package de.pls.stundenplaner.lesson;

import de.pls.stundenplaner.lesson.model.Lesson;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons/my")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<Lesson>> getLessons(
            final @RequestHeader UUID sessionID
    ) {
        return lessonService.getLessons(sessionID);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Lesson> getLesson(
            final @RequestHeader UUID sessionID,
            final @PathVariable int lessonId
    ) {
        return lessonService.getLesson(sessionID, lessonId);
    }

    @PostMapping
    public ResponseEntity<Lesson> createLesson(
            @RequestHeader("SessionID") UUID sessionId,
            @RequestBody Lesson lessonBody
    ) {
        return lessonService.createLesson(sessionId, lessonBody);
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Lesson> updateLesson(
            @RequestHeader("SessionID") UUID sessionId,
            @PathVariable int lessonId,
            @RequestBody @Validated Lesson lessonBody
    ) {
        return lessonService.updateLesson(sessionId, lessonId, lessonBody);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(
            @RequestHeader("SessionID") UUID sessionId,
            @PathVariable int lessonId
    ) {
        return lessonService.deleteLesson(sessionId, lessonId);
    }

}
