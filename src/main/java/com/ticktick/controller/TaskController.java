package com.ticktick.controller;

import com.ticktick.dto.request.TaskRequest;
import com.ticktick.dto.response.TaskResponse;
import com.ticktick.enums.TaskStatus;
import com.ticktick.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name = "Task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUserId(Integer userId) {
        return ResponseEntity.ok(taskService.findAllTasksByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Integer> save(@Valid @RequestBody TaskRequest taskRequest,
                                        Integer userId) {
        return ResponseEntity.ok(taskService.save(taskRequest, userId));
    }

    @PutMapping("/bulk")
    public ResponseEntity<List<Integer>> bulkTasks(@Valid @RequestBody List<TaskRequest> taskRequests,
                                                        @RequestParam Integer userId) {
        List<Integer> bulkTasks = taskService.bulkTasks(taskRequests, userId);
        return ResponseEntity.ok(bulkTasks);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(Integer taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Integer taskId,
                                                   @Valid @RequestBody TaskRequest taskRequest,
                                                   @RequestParam Integer userId) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskRequest, userId));
    }


    @PatchMapping("/{taskId}/delete")
    public ResponseEntity<Void> deleteTaskById(Integer taskId) {
        taskService.markAsDeleted(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Void> markAsComplete(@PathVariable Integer taskId) {
        taskService.markAsComplete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/important")
    public ResponseEntity<Void> markAsImportant(@PathVariable Integer taskId) {
        taskService.markAsImportant(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/urgent")
    public ResponseEntity<Void> markAsUrgent(@PathVariable Integer taskId) {
        taskService.markAsUrgent(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer taskId,
                                             @RequestParam TaskStatus status) {
        taskService.updateStatus(taskId, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/set-due-date")
    public ResponseEntity<Void> updateDueDate(@PathVariable Integer TaskId,
                                              @RequestParam LocalDate dueDate) {
        taskService.updateDueDate(TaskId, dueDate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}/set-focus-time")
    public ResponseEntity<Void> updateFocusTime(@PathVariable Integer taskId,
                                                @RequestParam Integer focusTime) {
        taskService.updateFocusTime(taskId, focusTime);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/important")
    public ResponseEntity<List<TaskResponse>> findImportantTask(Integer userId) {
        return ResponseEntity.ok(taskService.findImportantTask(userId));
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<TaskResponse>> findUrgentTask(Integer userId) {
        return ResponseEntity.ok(taskService.findUrgentTask(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> findTaskByStatus(Integer userId, TaskStatus status) {
        return ResponseEntity.ok(taskService.findTaskByStatus(userId, status));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskResponse>> findOverDueTask(Integer userId) {
        return ResponseEntity.ok(taskService.findOverDueTask(userId));
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<TaskResponse>> findDeletedTasks(Integer userId) {
        return ResponseEntity.ok(taskService.findDeletedTasks(userId));
    }

}
