package com.studyflow.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PersonalTimeBlocker(
        @SerializedName("name") String name,
        @SerializedName("description") String description,
        @SerializedName("start_date") OffsetDateTime startDate,
        @SerializedName("end_date") OffsetDateTime endDate,
        @SerializedName("user_id") UUID userId
) {}