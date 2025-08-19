package com.ticktick.controller;

import com.ticktick.dto.request.TaskRequest;
import com.ticktick.dto.response.TaskResponse;
import com.ticktick.enums.TaskStatus;
import com.ticktick.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name = "Task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByUserId(Authentication connectedUser) {
        return ResponseEntity.ok(taskService.findAllTasksByUserId(connectedUser));
    }

    @PostMapping
    public ResponseEntity<Integer> save(@Valid @RequestBody TaskRequest taskRequest,
                                        Authentication connectedUser) {
        return ResponseEntity.ok(taskService.save(taskRequest, connectedUser));
    }

    @PutMapping("/bulk")
    public ResponseEntity<List<Integer>> bulkTasks(@Valid @RequestBody List<TaskRequest> taskRequests,
                                                   Authentication connectedUser) {
        List<Integer> bulkTasks = taskService.bulkTasks(taskRequests, connectedUser);
        return ResponseEntity.ok(bulkTasks);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer taskId, Authentication connectedUser) {
        return ResponseEntity.ok(taskService.getTaskById(taskId, connectedUser));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Integer taskId,
                                                   @Valid @RequestBody TaskRequest taskRequest,
                                                   Authentication connectedUser) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskRequest, connectedUser));
    }


    @PatchMapping("/{taskId}/delete")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Integer taskId, Authentication connectedUser) {
        taskService.markAsDeleted(taskId, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Void> markAsComplete(@PathVariable Integer taskId, Authentication connectedUser) {
        taskService.markAsComplete(taskId, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/important")
    public ResponseEntity<Void> markAsImportant(@PathVariable Integer taskId, Authentication connectedUser) {
        taskService.markAsImportant(taskId, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/urgent")
    public ResponseEntity<Void> markAsUrgent(@PathVariable Integer taskId, Authentication connectedUser) {
        taskService.markAsUrgent(taskId, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer taskId,
                                             @RequestParam TaskStatus status,
                                             Authentication connectedUser) {
        taskService.updateStatus(taskId, status, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/set-due-date")
    public ResponseEntity<Void> updateDueDate(@PathVariable Integer TaskId,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
                                              Authentication connectedUser) {
        taskService.updateDueDate(TaskId, dueDate, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/set-focus-time")
    public ResponseEntity<Void> updateFocusTime(@PathVariable Integer taskId,
                                                @RequestParam Integer focusTime,
                                                Authentication connectedUser) {
        taskService.updateFocusTime(taskId, focusTime, connectedUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/important")
    public ResponseEntity<List<TaskResponse>> findImportantTask(Authentication connectedUser) {
        return ResponseEntity.ok(taskService.findImportantTask(connectedUser));
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<TaskResponse>> findUrgentTask(Authentication connectedUser) {
        return ResponseEntity.ok(taskService.findUrgentTask(connectedUser));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> findTaskByStatus(@PathVariable TaskStatus status, Authentication connectedUser) {
        return ResponseEntity.ok(taskService.findTaskByStatus( connectedUser,status));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskResponse>> findOverDueTask(Authentication connectedUser) {
        return ResponseEntity.ok(taskService.findOverDueTask(connectedUser));
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<TaskResponse>> findDeletedTasks(Authentication connectedUser) {
        return ResponseEntity.ok(taskService.findDeletedTasks(connectedUser));
    }

}
