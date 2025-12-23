package de.pls.stundenplaner.service;

import de.pls.stundenplaner.model.Exam;
import de.pls.stundenplaner.repository.ExamRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    /**
     * Gets All Exams available for a User and gives them with a proper {@link HttpStatus} back
     * @return StatusCode and a List of Exams
     */
    public ResponseEntity<List<Exam>> getAllExams(final UUID userUUID) {

        List<Exam> exams = examRepository.findExamsByUserUUID(userUUID);

        if (exams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    /**
     * Gets an Exam Entity by an Integer id
     * @param id ID to identify the Exam to be searched for
     * @return StatusCode and the Exam Entity
     */
    public ResponseEntity<Exam> getExam(
            final UUID userUUID,
            final int id
    ) {

        Optional<Exam> examToFind = examRepository.findExamByUserUUIDAndId(
                userUUID,
                id
        );

        return examToFind.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Updates an already existing Exam
     * @param userUUID UserUUID to search for
     * @param examId ExamId to search for
     * @param examUpdateInfos An Exam object containing the new values for the exam.
     * @return Proper {@link HttpStatus} with the updated Exam Object
     */
    public ResponseEntity<Exam> updateExam(
            final @NotNull UUID userUUID,
            final int examId,
            final @NotNull @Valid Exam examUpdateInfos
    ) {

        Optional<Exam> examOptional = examRepository.findExamByUserUUIDAndId(userUUID, examId);

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
     * @param userUUID UserUUID for searching in the Database
     * @param examToCreate Body as a {@link Exam}
     * @return Proper {@link HttpStatus} and valid {@link Exam} Entity
     */
    public ResponseEntity<Exam> createExam(
            final @NotNull UUID userUUID,
            final @NotNull @Valid Exam examToCreate
    ) {

        if (
                examToCreate.getNotes() == null ||
                        examToCreate.getNotes().isEmpty() ||
                        examToCreate.getDueDate() == null ||
                        examToCreate.getUserUUID() == null
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        examToCreate.setUserUUID(userUUID); // Ensures that it saves for the User given in the Parameters
        final Exam saved = examRepository.save(examToCreate);

        return new ResponseEntity<>(saved, HttpStatus.OK);

    }

    /**
     * Deletes an {@link Exam} Entity from the Database for a specified User
     * @param userUUID UserUUID to search for
     * @param examId ExamId to search for
     * @return Proper {@link HttpStatus}
     */
    public ResponseEntity<Void> deleteExam(
            final @NotNull @Valid UUID userUUID,
            final @NotNull @Valid int examId
    ) {

        Optional<Exam> examToDelete = examRepository.findExamByUserUUIDAndId(userUUID, examId);

        if (examToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examRepository.delete(examToDelete.get());
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
