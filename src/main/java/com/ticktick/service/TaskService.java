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

    public List<Integer> bulkTasks(List<TaskRequest> taskRequests, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with Id: " + userId));

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

    public TaskResponse getTaskById(Integer taskId){
        return taskRepository.findById(taskId)
                .map(taskMapper::toTaskResponse)
                .orElseThrow(() -> new RuntimeException("Task not found with Id: " + taskId));
    }


    public TaskResponse updateTask(Integer taskId, TaskRequest taskRequest, Integer userId){
        Task exitTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not Found with Id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found wiht Id: "+ userId));

        Task updatedTask = taskMapper.updateTask(exitTask, taskRequest);
        updatedTask.setUser(user);

        Task savedTask = taskRepository.save(updatedTask);

        return taskMapper.toTaskResponse(savedTask);
    }

    public void markAsDeleted(Integer taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        task.setDeleted(!task.getDeleted());
        taskRepository.save(task);
    }

     public void markAsComplete(Integer taskId) {
         Task task = taskRepository.findById(taskId)
                 .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));

         task.setStatus(TaskStatus.COMPLETED);
         task.setCompletedAt(LocalDateTime.now());
         taskRepository.save(task);
     }

     public void markAsImportant(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                 .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        task.setImportant(!task.getImportant());
         taskRepository.save(task);
     }

     public void markAsUrgent(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        task.setUrgent(!task.getUrgent());
        taskRepository.save(task);
     }

     public void updateStatus(Integer taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        task.setStatus(status);
        taskRepository.save(task);
     }

     public void updateDueDate(Integer taskId, LocalDate dueDate) {
         Task task = taskRepository.findById(taskId)
                 .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
         task.setDueDate(dueDate);
         taskRepository.save(task);
     }

     public void updateFocusTime(Integer taskId, Integer focusTime) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with this Id: " + taskId));
        task.setFocusTime(focusTime);
        taskRepository.save(task);
     }

     public List<TaskResponse> findImportantTask(Integer userId){
         return taskRepository.findImportantTask(userId).stream()
                 .map(taskMapper::toTaskResponse)
                 .toList();
     }

     public List<TaskResponse> findUrgentTask(Integer userId){
        return taskRepository.findUrgentTask(userId).stream()
                 .map(taskMapper::toTaskResponse)
                 .toList();
     }

     public List<TaskResponse> findTaskByStatus(Integer userId, TaskStatus Status){
        return taskRepository.findTaskByStatus(userId, Status).stream()
                 .map(taskMapper::toTaskResponse)
                 .toList();
     }

     public List<TaskResponse> findOverDueTask(Integer userId){
        return taskRepository.findOverdueTask(userId, LocalDate.now()).stream()
                 .map(taskMapper::toTaskResponse)
                 .toList();
     }

     public List<TaskResponse> findDeletedTasks(Integer userId){
        return taskRepository.findDeletedTask(userId).stream()
                .map(taskMapper::toTaskResponse)
                .toList();
     }

}
