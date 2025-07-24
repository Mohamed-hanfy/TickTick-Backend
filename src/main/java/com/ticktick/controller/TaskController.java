package com.ticktick.controller;

import com.ticktick.dto.request.TaskRequest;
import com.ticktick.dto.response.TaskResponse;
import com.ticktick.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name = "Task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUserId(Integer userId){
        return ResponseEntity.ok(taskService.findAllTasksByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Integer> save(@Valid @RequestBody TaskRequest taskRequest,
                                        Integer userId){
        return ResponseEntity.ok(taskService.save(taskRequest, userId));
    }


}
