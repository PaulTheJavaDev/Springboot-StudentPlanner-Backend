package de.pls.stundenplaner.exam;

import de.pls.stundenplaner.exam.model.Exam;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.auth.exceptions.InvalidSessionException;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public ExamService(ExamRepository examRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<Exam>> getAllExams(
            @NonNull UUID sessionID
    ) {
        User user = validateSession(sessionID);

        List<Exam> exams = examRepository.findExamsByUserUUID(user.getUserUUID());

        if (exams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    public ResponseEntity<Exam> getExam(
            @NonNull UUID sessionID,
            final int examId
    ) {
        validateSession(sessionID);

        Optional<Exam> examToFind = examRepository.findById(examId);

        return examToFind.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<Exam> updateExam(
            @NonNull UUID sessionID,
            final int examId,
            @NonNull @Valid Exam examUpdateInfos
    ) {

        User user = validateSession(sessionID);

        Optional<Exam> examOptional = examRepository.findById(examId);

        if (examOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Exam exam = examOptional.get();

        exam.setNotes(examUpdateInfos.getNotes());
        exam.setSubject(examUpdateInfos.getSubject());
        exam.setDueDate(examUpdateInfos.getDueDate());
        exam.setUserUUID(user.getUserUUID());

        return new ResponseEntity<>(examRepository.save(exam), HttpStatus.OK);

    }

    public ResponseEntity<Exam> createExam(
            @NonNull UUID sessionID,
            @NonNull @Valid Exam examToCreate
    ) {
        User user = validateSession(sessionID);

        if (
                examToCreate.getNotes() == null ||
                examToCreate.getNotes().isEmpty() ||
                examToCreate.getDueDate() == null
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        examToCreate.setUserUUID(user.getUserUUID());
        final Exam savedExam = examRepository.save(examToCreate);

        return new ResponseEntity<>(savedExam, HttpStatus.OK);

    }

    public ResponseEntity<Void> deleteExam(
            @NonNull UUID sessionID,
            final int examId
    ) {
        validateSession(sessionID);

        Optional<Exam> examToDelete = examRepository.findById(examId);

        if (examToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examRepository.delete(examToDelete.get());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private User validateSession(@NonNull UUID sessionID) {
        return userRepository.findBySessionID(sessionID).orElseThrow(InvalidSessionException::new);
    }

}
