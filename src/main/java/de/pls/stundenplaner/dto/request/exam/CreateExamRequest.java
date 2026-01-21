package de.pls.stundenplaner.dto.request.exam;

import de.pls.stundenplaner.model.Subject;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateExamRequest {

    Subject subject;
    LocalDate dueDate;
    String notes;

}
