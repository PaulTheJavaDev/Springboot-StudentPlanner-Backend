package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoAssignmentRepository extends MongoRepository<Assignment, ObjectId> {

    List<Assignment> findByIdentifier(String identifier);

    Assignment findAssignmentByIdentifierAndId(String identifier, ObjectId id);
}