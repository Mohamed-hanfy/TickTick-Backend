package com.ticktick.mapper;


import com.ticktick.dto.request.TaskRequest;
import com.ticktick.dto.response.TaskResponse;
import com.ticktick.model.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public Task toTask(TaskRequest taskRequest){
        return Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .important(taskRequest.important())
                .urgent(taskRequest.urgent())
                .dueDate(taskRequest.dueDate())
                .focusTime(taskRequest.focusTime())
                .status(taskRequest.status())
                .build();
    }

    public TaskResponse toTaskResponse(Task task){
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .important(task.getImportant())
                .urgent(task.getUrgent())
                .dueDate(task.getDueDate().toString())
                .focusTime(task.getFocusTime())
                .status(task.getStatus().name())
                .createdAt(task.getCreatedAt().toString())
                .build();
    }

    public Task updateTask(Task exitsingTask, TaskRequest taskRequest){

        exitsingTask.setTitle(taskRequest.title());
        exitsingTask.setDescription(taskRequest.description());
        exitsingTask.setImportant(taskRequest.important());
        exitsingTask.setUrgent(taskRequest.urgent());
        exitsingTask.setDueDate(taskRequest.dueDate());
        exitsingTask.setFocusTime(taskRequest.focusTime());
        exitsingTask.setStatus(taskRequest.status());

        return exitsingTask;
    }

}
