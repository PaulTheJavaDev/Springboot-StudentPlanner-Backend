package de.pls.stundenplaner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "assignments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    @Id
    private ObjectId id;

    private String identifier;
    private Subject subject;
    private Date dueDate;

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public ObjectId getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Subject getSubject() {
        return subject;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
