package de.pls.stundenplaner.service;

import de.pls.stundenplaner.dto.request.assignment.CreateAssignmentRequest;
import de.pls.stundenplaner.dto.request.assignment.UpdateAssignmentRequest;
import de.pls.stundenplaner.repository.AssignmentRepository;
import de.pls.stundenplaner.model.AssignmentEntity;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.util.exceptions.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.pls.stundenplaner.util.model.UserUtil.checkUserExistenceBySessionID;

@Service
@RequiredArgsConstructor
public final class AssignmentService {

    private final AssignmentRepository assignmentRepository;


    public List<AssignmentEntity> getAssignments(
            final @NotNull UUID sessionID
    ) {

        User user = validateSession(sessionID);

        return assignmentRepository.findAssignmentsByUserUUID(user.getUserUUID());

    }

    public AssignmentEntity createAssignment(
            @NotNull UUID sessionID,
            @Valid CreateAssignmentRequest request
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        if (request.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past!");
        }

        AssignmentEntity entity = new AssignmentEntity();
        entity.setSubject(request.getSubject());
        entity.setDueDate(request.getDueDate());
        entity.setCompleted(request.isCompleted());
        entity.setNotes(request.getNotes());
        entity.setUserUUID(user.getUserUUID());

        return assignmentRepository.save(entity);
    }

    public AssignmentEntity updateAssignment(
            @NotNull UUID sessionID,
            final int assignmentId,
            UpdateAssignmentRequest request
    ) {

        User user = validateSession(sessionID);

        final AssignmentEntity assignmentEntity = assignmentRepository.findById(assignmentId).orElseThrow(EntityNotFoundException::new);

        if (!assignmentEntity.getUserUUID().equals(user.getUserUUID())) {
            throw new UnauthorizedException("User is not authorized to update this assignment");
        }

        assignmentEntity.setSubject(request.getSubject());
        assignmentEntity.setDueDate(request.getDueDate());
        assignmentEntity.setCompleted(request.isCompleted());

        assignmentRepository.save(assignmentEntity);

        return assignmentEntity;
    }

    public void deleteAssignment(
            final UUID sessionID,
            final int assignmentId
    ) {
        validateSession(sessionID);

        final Optional<AssignmentEntity> assignmentToDelete = assignmentRepository.findById(assignmentId);

        if (assignmentToDelete.isEmpty()) {
            throw new EntityNotFoundException("Assignment with id:" + assignmentId + " was not found!");
        }

        assignmentRepository.delete(assignmentToDelete.get());
    }

    private User validateSession(
            final @NotNull UUID sessionID
    ) {
        return checkUserExistenceBySessionID(sessionID);
    }
}
