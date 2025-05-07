package com.studyflow.service;

import com.studyflow.model.assignment.Assignment;
import com.studyflow.repository.AssignmentRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssignmentService {

    private final Jdbi jdbi;

    public AssignmentService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createAssignment(Assignment assignment) {
        if (assignment.getId() == null) {
            assignment.setId(UUID.randomUUID());
        }

        jdbi.useExtension(AssignmentRepository.class, repo -> repo.insertAssignment(assignment));
    }

    public void updateAssignment(UUID id, Assignment assignment) {
        assignment.setId(id);
        jdbi.useExtension(AssignmentRepository.class, repo -> repo.updateAssignment(assignment));
    }

    public void deleteAssignment(UUID id) {
        jdbi.useExtension(AssignmentRepository.class, repo -> repo.deleteAssignment(id));
    }
}
