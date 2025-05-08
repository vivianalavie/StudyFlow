package com.studyflow.controller;

import com.studyflow.model.PersonalTimeBlocker;
import com.studyflow.repository.PersonalTimeBlockerRepository;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/time-blocker")
public class PersonalTimeBlockerController {

    private static final Logger log = LoggerFactory.getLogger(PersonalTimeBlockerController.class);
    private final PersonalTimeBlockerRepository repository;

    public PersonalTimeBlockerController(Jdbi jdbi) {
        this.repository = jdbi.onDemand(PersonalTimeBlockerRepository.class);
    }

    @PostMapping
    public void createTimeBlocker(@RequestBody PersonalTimeBlocker block) {
        try {
            repository.createTimeBlocker(
                    block.name(),
                    block.description(),
                    block.startDate(),
                    block.endDate(),
                    block.userId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
