package com.ticktick.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {
}
