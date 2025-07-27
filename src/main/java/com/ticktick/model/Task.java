package com.ticktick.model;

import com.ticktick.constants.TaskConstants;
import com.ticktick.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ticktick.constants.TaskConstants.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")

@NamedQuery(name = FIND_TASK_BY_USER_ID,
        query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.deleted = false ORDER BY t.createdAt DESC")

@NamedQuery(name = FIND_IMPORTANT_TASK,
query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.important = true AND t.deleted = false ORDER BY t.createdAt DESC")

@NamedQuery(name = FIND_URGENT_TASK,
query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.urgent = true AND t.deleted = false ORDER BY t.createdAt DESC")

@NamedQuery(name = FIND_TASK_BY_STATUS,
        query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.status = :status AND t.deleted = false ORDER BY t.createdAt DESC")

@NamedQuery(name = FIND_OVERDUE_TASK,
query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.dueDate < :date AND t.deleted = false ORDER BY t.createdAt DESC")

@NamedQuery(name = FIND_DELETED_TASK ,
        query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.deleted = true ORDER BY t.createdAt DESC")

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    private Boolean important = false;

    @Builder.Default
    private Boolean urgent = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dueDate;

    private Integer focusTime;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime completedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    private Boolean deleted=false;

}
