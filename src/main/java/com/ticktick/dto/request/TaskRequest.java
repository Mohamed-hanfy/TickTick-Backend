package com.ticktick.dto.request;

import com.ticktick.enums.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequest(
        Integer Id,

        @NotEmpty(message = "Title is required and can't be empty")
        String title,

        String description,

        Boolean important,

        Boolean urgent,

        LocalDate dueDate,

        Integer focusTime,

        @NotNull(message = "Status is required")
        TaskStatus status
) {
}
