package de.pls.stundenplaner.service;

import de.pls.stundenplaner.dto.request.exam.CreateExamRequest;
import de.pls.stundenplaner.dto.request.exam.UpdateExamRequest;
import de.pls.stundenplaner.model.Exam;
import de.pls.stundenplaner.repository.ExamRepository;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
import de.pls.stundenplaner.util.exceptions.UnauthorizedAccessException;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.pls.stundenplaner.util.model.UserUtil.checkUserExistenceBySessionID;

/**
 * Business logic for the {@link Exam} entity.
 */
@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    /**
     * Retrieves all exams for a user.
     *
     * @param sessionID Used to determine the user by searching the session ID in the database.
     * @return A list of exams. Returns an empty list if none are found.
     * @throws InvalidSessionException Thrown when the request contains no session ID or an invalid session ID.
     */
    public List<Exam> getAllExams(
            @NonNull UUID sessionID
    ) throws InvalidSessionException {

        User user = checkUserExistenceBySessionID(sessionID);
        return examRepository.findExamsByUserUUID(user.getUserUUID());
    }

    /**
     * Creates an exam for a specified user.
     *
     * @param sessionID Used to determine the user by searching the session ID in the database.
     * @param createExamRequest A DTO used to create an exam. Contains only the required information.
     * @return The created exam.
     * @throws InvalidSessionException Thrown when the request contains no session ID or an invalid session ID.
     * @throws UnauthorizedAccessException Thrown when a user attempts to create an exam for another user.
     */
    public Exam createExam(
            @NotNull final UUID sessionID,
            @NotNull final CreateExamRequest createExamRequest
    ) throws InvalidSessionException, UnauthorizedAccessException {

        User user = checkUserExistenceBySessionID(sessionID);

        LocalDate today = LocalDate.now();

        if (createExamRequest.getDueDate().isBefore(today)) {
            throw new DateTimeException("Date cannot be in the past.");
        }

        Exam exam = new Exam();

        if (!exam.getUserUUID().equals(user.getUserUUID())) {
            throw new UnauthorizedAccessException("User is not authorized to create this exam.");
        }

        exam.setSubject(createExamRequest.getSubject());
        exam.setNotes(createExamRequest.getNotes());
        exam.setDueDate(createExamRequest.getDueDate());
        exam.setUserUUID(user.getUserUUID());

        examRepository.save(exam);

        return exam;
    }

    /**
     * Updates an existing exam.
     *
     * @param sessionID Used to determine the user by searching the session ID in the database.
     * @param request A DTO used to update an exam. Contains only the required information.
     * @param examId Used to find the associated exam in the database.
     * @return The updated exam.
     * @throws InvalidSessionException Thrown when the session ID is invalid.
     * @throws UnauthorizedAccessException Thrown if the exam does not belong to the user.
     * @throws EntityNotFoundException Thrown if the exam does not exist.
     */
    public Exam updateExam(
            @NotNull final UUID sessionID,
            @NotNull final UpdateExamRequest request,
            final int examId
    ) throws InvalidSessionException, UnauthorizedAccessException {

        User user = checkUserExistenceBySessionID(sessionID);

        Optional<Exam> examOptional = examRepository.findById(examId);

        if (examOptional.isEmpty()) {
            throw new EntityNotFoundException("Exam with ID " + examId + " was not found.");
        }

        Exam exam = examOptional.get();

        if (!exam.getUserUUID().equals(user.getUserUUID())) {
            throw new UnauthorizedAccessException("User is not authorized to update this exam.");
        }

        exam.setNotes(request.getNotes());
        exam.setSubject(request.getSubject());
        exam.setDueDate(request.getDueDate());
        exam.setUserUUID(user.getUserUUID());

        examRepository.save(exam);

        return exam;
    }

    /**
     * Deletes an existing exam.
     *
     * @param sessionID Used to determine the user by searching the session ID in the database.
     * @param examId Used to find the associated exam in the database.
     * @throws InvalidSessionException Thrown when the session ID is invalid.
     * @throws EntityNotFoundException Thrown if the exam does not exist.
     */
    public void deleteExam(
            @NotNull final UUID sessionID,
            final int examId
    ) throws InvalidSessionException, EntityNotFoundException {

        checkUserExistenceBySessionID(sessionID);
        Exam examToDelete = examRepository.findById(examId)
                .orElseThrow(EntityNotFoundException::new);

        examRepository.delete(examToDelete);
    }
}
