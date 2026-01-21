package de.pls.stundenplaner.service;

import de.pls.stundenplaner.dto.request.exam.CreateExamRequest;
import de.pls.stundenplaner.dto.request.exam.UpdateExamRequest;
import de.pls.stundenplaner.model.ExamEntity;
import de.pls.stundenplaner.repository.ExamRepository;
import de.pls.stundenplaner.model.User;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.pls.stundenplaner.util.model.UserUtil.checkUserExistenceBySessionID;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public List<ExamEntity> getAllExams(
            @NonNull UUID sessionID
    ) {

        User user = checkUserExistenceBySessionID(sessionID);
        return examRepository.findExamsByUserUUID(user.getUserUUID());

    }

    public ExamEntity updateExam(
            @NonNull UUID sessionID,
            final int examId,
            UpdateExamRequest request
    ) {

        User user = checkUserExistenceBySessionID(sessionID);

        Optional<ExamEntity> examOptional = examRepository.findById(examId);

        if (examOptional.isEmpty()) {
            throw new EntityNotFoundException("Exam with id:" + examId + " was not found!");
        }

        ExamEntity exam = examOptional.get();

        exam.setNotes(request.getNotes());
        exam.setSubject(request.getSubject());
        exam.setDueDate(request.getDueDate());
        exam.setUserUUID(user.getUserUUID());

        examRepository.save(exam);

        return exam;

    }

    public ExamEntity createExam(
            @NonNull UUID sessionID,
            CreateExamRequest request
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        LocalDate today = LocalDate.now();

        if (request.getDueDate().isBefore(today)) {
            throw new DateTimeException("Date cannot be in the past!");
        }

        ExamEntity exam = new ExamEntity();
        exam.setSubject(request.getSubject());
        exam.setNotes(request.getNotes());
        exam.setDueDate(request.getDueDate());
        exam.setUserUUID(user.getUserUUID());

        examRepository.save(exam);

        return exam;

    }

    public void deleteExam(
            @NonNull UUID sessionID,
            final int examId
    ) {

        checkUserExistenceBySessionID(sessionID);
        ExamEntity examToDelete = examRepository.findById(examId).orElseThrow(EntityNotFoundException::new);
        examRepository.delete(examToDelete);

    }

}