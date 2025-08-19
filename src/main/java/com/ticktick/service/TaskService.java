package com.ticktick.service;

import com.ticktick.dto.request.TaskRequest;
import com.ticktick.dto.response.TaskResponse;
import com.ticktick.enums.TaskStatus;
import com.ticktick.mapper.TaskMapper;
import com.ticktick.model.Task;
import com.ticktick.model.User;
import com.ticktick.repository.TaskRepository;
import com.ticktick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Integer save(TaskRequest taskRequest, Authentication connectedUser) {
        User user = ((User) (connectedUser.getPrincipal()));
        Task task = taskMapper.toTask(taskRequest);
        task.setUser(user);
        return taskRepository.save(task).getId();
    }

    public List<TaskResponse> findAllTasksByUserId(Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        return taskRepository.findTaskByUserId(userId).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public List<Integer> bulkTasks(List<TaskRequest> taskRequests, Authentication connectedUser) {
        User user = ((User) (connectedUser.getPrincipal()));

        List<Integer> bulkTasks = new ArrayList<>();

        for (TaskRequest taskRequest : taskRequests) {
            if (taskRequest.Id() != null) {

                Task existingTask = taskRepository.findById(taskRequest.Id())
                        .orElseThrow(() -> new RuntimeException("Task not found with Id: " + taskRequest.Id()));

                Task updatedTask = taskMapper.updateTask(existingTask, taskRequest);
                updatedTask.setUser(user);

                Task savedTask = taskRepository.save(updatedTask);
                bulkTasks.add(savedTask.getId());
            } else {
                Task newTask = taskMapper.toTask(taskRequest);
                newTask.setUser(user);

                Task savedTask = taskRepository.save(newTask);
                bulkTasks.add(savedTask.getId());
            }
        }

        return bulkTasks;
    }

    public TaskResponse getTaskById(Integer taskId, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with Id: " + taskId));

        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }

        return taskMapper.toTaskResponse(task);
    }


    public TaskResponse updateTask(Integer taskId, TaskRequest taskRequest, Authentication connectedUser) {
        Task exitTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not Found with Id: " + taskId));

        User user = ((User) (connectedUser.getPrincipal()));

        Task updatedTask = taskMapper.updateTask(exitTask, taskRequest);
        updatedTask.setUser(user);

        Task savedTask = taskRepository.save(updatedTask);

        return taskMapper.toTaskResponse(savedTask);
    }

    public void markAsDeleted(Integer taskId, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }
        task.setDeleted(!task.getDeleted());
        taskRepository.save(task);
    }

    public void markAsComplete(Integer taskId, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("Task already completed");
        }

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void markAsImportant(Integer taskId, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));

        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }

        task.setImportant(!task.getImportant());
        taskRepository.save(task);
    }

    public void markAsUrgent(Integer taskId, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);

        Task task = taskRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));

        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }

        boolean CurrentlyUrgent = Boolean.TRUE.equals(task.getUrgent());
        task.setUrgent(!CurrentlyUrgent);
        taskRepository.save(task);
    }

    public void updateStatus(Integer taskId, TaskStatus status, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));

        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }
        if (task.getStatus() == status) {
            throw new RuntimeException("Task already in this status");
        }
        task.setStatus(status);
        taskRepository.save(task);
    }

    public void updateDueDate(Integer taskId, LocalDate dueDate, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }
        task.setDueDate(dueDate);
        taskRepository.save(task);
    }

    public void updateFocusTime(Integer taskId, Integer focusTime, Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        if (task.getUser().getId() != userId) {
            throw new RuntimeException("Task not found with this Id: " + taskId);
        }
        task.setFocusTime(focusTime);
        taskRepository.save(task);
    }

    public List<TaskResponse> findImportantTask(Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        return taskRepository.findImportantTask(userId).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public List<TaskResponse> findUrgentTask(Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        return taskRepository.findUrgentTask(userId).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public List<TaskResponse> findTaskByStatus(Authentication connectedUser, TaskStatus Status) {
        Integer userId = getCurrentUserId(connectedUser);
        return taskRepository.findTaskByStatus(userId, Status).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public List<TaskResponse> findOverDueTask(Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        return taskRepository.findOverdueTask(userId, LocalDate.now()).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public List<TaskResponse> findDeletedTasks(Authentication connectedUser) {
        Integer userId = getCurrentUserId(connectedUser);
        return taskRepository.findDeletedTask(userId).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    private Integer getCurrentUserId(Authentication connectedUser) {
        User user = ((User) (connectedUser.getPrincipal()));
        return user.getId();
    }
}
