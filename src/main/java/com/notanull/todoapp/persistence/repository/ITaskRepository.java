package com.notanull.todoapp.persistence.repository;

import com.notanull.todoapp.persistence.entity.Task;
import com.notanull.todoapp.persistence.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByTaskStatus(TaskStatus taskStatus); //JPA creates a query through the method name

    //Native query
    @Modifying
    @Query(value = "UPDATE TASK SET FINISHED=true WHERE ID=:id", nativeQuery = true)
    void markTaskAsFinished(/*@Param("id")*/ Long id);
}
