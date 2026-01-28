package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.Exam;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    @Query("SELECT e FROM Exam e WHERE e.userUUID = :userUUID")
    List<Exam> findExamsByUserUUID(final @NotNull UUID userUUID);

}