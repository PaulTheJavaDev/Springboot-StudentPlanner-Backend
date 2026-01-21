package de.pls.stundenplaner.dto.request.assignment;

import de.pls.stundenplaner.model.Subject;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateAssignmentRequest {

    Subject subject;
    boolean isCompleted;
    LocalDate dueDate;

}
