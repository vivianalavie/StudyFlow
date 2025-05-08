package com.studyflow.controller;

import com.studyflow.model.PersonalTimeblocker;
import com.studyflow.repository.PersonalTimeblockerRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.web.bind.annotation.*;

        import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/timeblocks")
public class PersonalTimeblockerController {

    private final PersonalTimeblockerRepository repository;

    public PersonalTimeblockerController(Jdbi jdbi) {
        this.repository = jdbi.onDemand(PersonalTimeblockerRepository.class);
    }

    @PostMapping
    public void createTimeblock(@RequestBody PersonalTimeblocker block) {
        block.setId(UUID.randomUUID());
        if (block.getStartDate() == null) block.setStartDate(OffsetDateTime.now());
        repository.insertTimeblock(block);
    }
}
