package de.pls.stundenplaner.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

class ExamTests {

    @Test
    void testExamGettersAndSetters() {

        final Subject subject = Subject.MATH;
        final UUID userUUID = UUID.randomUUID();
        final LocalDate dueDate = LocalDate.now();

        final Exam exam = new Exam();
        exam.setUserUUID(userUUID);
        exam.setDueDate(dueDate);
        exam.setSubject(subject);

        assertEquals(userUUID, exam.getUserUUID());
        assertEquals(dueDate, exam.getDueDate());
        assertEquals(subject, exam.getSubject());

    }

}
