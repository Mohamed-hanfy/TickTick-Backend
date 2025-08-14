package com.ticktick.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record RegisterRequest(
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Password is required")
        String password) {
}
