package com.studyflow.model.auth;

import java.util.Optional;

public record SignUpResponse(
        Optional<String> id
) {
}
