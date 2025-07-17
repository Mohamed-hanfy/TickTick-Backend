package com.ticktick.repository;

import com.ticktick.constants.TaskConstants;
import com.ticktick.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(name= TaskConstants.FIND_TASK_BY_USER_ID)
    List<Task> findTaskByUserId(int userId);
}
