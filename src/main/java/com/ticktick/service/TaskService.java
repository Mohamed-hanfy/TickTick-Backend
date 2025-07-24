package com.ticktick.service;

import com.ticktick.dto.request.TaskRequest;
import com.ticktick.dto.response.TaskResponse;
import com.ticktick.mapper.TaskMapper;
import com.ticktick.model.Task;
import com.ticktick.model.User;
import com.ticktick.repository.TaskRepository;
import com.ticktick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Integer save(TaskRequest taskRequest, Integer userId){
        Task task = taskMapper.toTask(taskRequest);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        task.setUser(user);
        return taskRepository.save(task).getId();
    }

    public List<TaskResponse> findAllTasksByUserId(Integer userId){
        return taskRepository.findTaskByUserId(userId).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }
}
