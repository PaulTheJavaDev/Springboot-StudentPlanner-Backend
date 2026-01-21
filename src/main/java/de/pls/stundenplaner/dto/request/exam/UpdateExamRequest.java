package de.pls.stundenplaner.dto.request.exam;

import de.pls.stundenplaner.model.Subject;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateExamRequest {

    Subject subject;
    String notes;
    LocalDate dueDate;

}
