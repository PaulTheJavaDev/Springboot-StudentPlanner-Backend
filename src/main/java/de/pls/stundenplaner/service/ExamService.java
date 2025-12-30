package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Exam;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.repository.ExamRepository;
import de.pls.stundenplaner.repository.UserRepository;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 */
@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public ExamService(ExamRepository examRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    /**
     * Gets All Exams available for a User and gives them with a proper {@link HttpStatus} back
     *
     * @return StatusCode and a List of Exams
     */
    public ResponseEntity<List<Exam>> getAllExams(
            final UUID sessionID
    ) {

        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        List<Exam> exams = examRepository.findExamsByUserUUID(user.getUserUUID());

        if (exams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    /**
     * Gets an Exam Entity by an Integer examId
     *
     * @param examId ID to identify the Exam to be searched for
     * @return StatusCode and the Exam Entity
     */
    public ResponseEntity<Exam> getExam(
            final int examId
    ) {

        Optional<Exam> examToFind = examRepository.findById(examId);

        return examToFind.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Updates an already existing Exam
     *
     * @param examUpdateInfos An Exam object containing the new values for the exam.
     * @return Proper {@link HttpStatus} with the updated Exam Object
     */
    public ResponseEntity<Exam> updateExam(
            final int examId,
            final @NotNull @Valid Exam examUpdateInfos
    ) {

        Optional<Exam> examOptional = examRepository.findById(examId);

        if (examOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Exam exam = examOptional.get();

        exam.setNotes(examUpdateInfos.getNotes());
        exam.setSubject(examUpdateInfos.getSubject());
        exam.setDueDate(examUpdateInfos.getDueDate());

        return new ResponseEntity<>(examRepository.save(exam), HttpStatus.OK);

    }

    /**
     *
     * @param examToCreate Body as a {@link Exam}
     * @return Proper {@link HttpStatus} and valid {@link Exam} Entity
     */
    public ResponseEntity<Exam> createExam(
            final @NotNull @Valid Exam examToCreate
    ) {

        if (
                examToCreate.getNotes() == null ||
                examToCreate.getNotes().isEmpty() ||
                examToCreate.getDueDate() == null
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Exam saved = examRepository.save(examToCreate);

        return new ResponseEntity<>(saved, HttpStatus.OK);

    }

    /**
     * Deletes an {@link Exam} Entity from the Database for a specified User
     *
     * @return Proper {@link HttpStatus}
     */
    public ResponseEntity<Void> deleteExam(
            final @NotNull @Valid int examId
    ) {

        Optional<Exam> examToDelete = examRepository.findById(examId);

        if (examToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examRepository.delete(examToDelete.get());
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
