package com.ticktick.repository;

import com.ticktick.constants.TaskConstants;
import com.ticktick.enums.TaskStatus;
import com.ticktick.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

import static com.ticktick.constants.TaskConstants.*;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(name= FIND_TASK_BY_USER_ID)
    List<Task> findTaskByUserId(int userId);

    @Query(name= FIND_IMPORTANT_TASK)
    List<Task> findImportantTask(int userId);

    @Query(name= FIND_URGENT_TASK)
    List<Task> findUrgentTask(int userId);

    @Query(name= FIND_TASK_BY_STATUS)
    List<Task> findTaskByStatus(int userId, TaskStatus status);

    @Query(name= FIND_OVERDUE_TASK)
    List<Task> findOverdueTask(int userId, LocalDate date);

    @Query(name= FIND_DELETED_TASK)
    List<Task> findDeletedTask(int userId);

}
