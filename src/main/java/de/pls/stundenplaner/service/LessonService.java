package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Lesson;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.repository.LessonRepository;
import de.pls.stundenplaner.repository.UserRepository;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public LessonService(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<Lesson>> getLessons(@NotNull UUID sessionID) {

        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        List<Lesson> lessons = lessonRepository.findAllByUserUUID(user.getUserUUID());

        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }


    public ResponseEntity<Lesson> getLesson(
            final int lessonId
    ) {

        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);

        return optionalLesson.map(
                lesson -> new ResponseEntity<>(lesson, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    // POST
    public ResponseEntity<Lesson> createLesson(
            @NonNull UUID sessionId,
            @NonNull Lesson lessonBody
    ) {

        User user = userRepository.findBySessionID(sessionId)
                        .orElseThrow(InvalidSessionException::new);

        lessonBody.setUserUUID(user.getUserUUID());
        lessonRepository.save(lessonBody);

        return new ResponseEntity<>(lessonBody, HttpStatus.CREATED);
    }

    // PUT
    public ResponseEntity<Lesson> updateLesson(
            final int lessonId,
            final Lesson lessonBody
    ) {

        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);

        if (optionalLesson.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Lesson lesson = optionalLesson.get();
        lesson.setSubject(lessonBody.getSubject());
        lesson.setStartTime(lessonBody.getStartTime());
        lesson.setEndTime(lessonBody.getEndTime());

        return new ResponseEntity<>(lessonRepository.save(lesson), HttpStatus.OK);

    }

    // DELETE

    public ResponseEntity<Void> deleteLesson(
            final int lessonId
    ) {

        lessonRepository.deleteById(lessonId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
