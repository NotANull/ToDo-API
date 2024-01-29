package com.notanull.todoapp.service;



import com.notanull.todoapp.dto.request.TaskDto;
import com.notanull.todoapp.dto.response.ResponseTask;
import com.notanull.todoapp.persistence.entity.TaskStatus;

import java.util.List;

public interface ITaskService {

    TaskDto createTask(TaskDto taskDto);

    List<ResponseTask> findAll();

    List<ResponseTask> findAllTaskStatus(TaskStatus taskStatus);

    String updateTaskAsFinished(Long id);

    String deleteById(Long id);
}
