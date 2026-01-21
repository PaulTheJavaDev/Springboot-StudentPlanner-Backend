package de.pls.stundenplaner.dto.request.assignment;

import de.pls.stundenplaner.model.Subject;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAssignmentRequest {

    Subject subject;
    LocalDate dueDate;
    boolean completed = false;
    String notes;

}
