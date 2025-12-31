package de.pls.stundenplaner.lesson;

import de.pls.stundenplaner.auth.exceptions.InvalidSessionException;
import de.pls.stundenplaner.lesson.model.Lesson;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.user.model.User;
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

    public ResponseEntity<List<Lesson>> getLessons(
            final UUID sessionID
    ) {

        User user = validateSession(sessionID);

        List<Lesson> lessons = lessonRepository.findAllByUserUUID(user.getUserUUID());

        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }


    public ResponseEntity<Lesson> getLesson(
            final UUID sessionID,
            final int lessonId
    ) {
        validateSession(sessionID);

        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);

        return optionalLesson.map(
                lesson -> new ResponseEntity<>(lesson, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    public ResponseEntity<Lesson> createLesson(
            final UUID sessionID,
            final Lesson lessonBody
    ) {

        User user = validateSession(sessionID);

        lessonBody.setUserUUID(user.getUserUUID());
        lessonRepository.save(lessonBody);

        return new ResponseEntity<>(lessonBody, HttpStatus.CREATED);
    }

    public ResponseEntity<Lesson> updateLesson(
            final UUID sessionID,
            final int lessonId,
            final Lesson lessonBody
    ) {
        validateSession(sessionID);

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

    public ResponseEntity<Void> deleteLesson(
            final UUID sessionID,
            final int lessonId
    ) {
        validateSession(sessionID);

        lessonRepository.deleteById(lessonId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private User validateSession(UUID sessionID) {
        return userRepository.findBySessionID(sessionID).orElseThrow(InvalidSessionException::new);
    }
}
