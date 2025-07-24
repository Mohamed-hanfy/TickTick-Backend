package com.ticktick.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Integer id;
    private String title;
    private String description;
    private Boolean important;
    private Boolean urgent;
    private String dueDate;
    private Integer focusTime;
    private String status;
    private String createdAt;
}
