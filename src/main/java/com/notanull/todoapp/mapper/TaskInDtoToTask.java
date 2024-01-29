package com.notanull.todoapp.mapper;

import com.notanull.todoapp.dto.request.TaskDto;
import com.notanull.todoapp.persistence.entity.Task;
import com.notanull.todoapp.persistence.entity.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskInDtoToTask implements ITaskMapper<TaskDto, Task>{
    @Override
    public Task map(TaskDto in) {
        Task task = new Task();
        task.setTitle(in.getTitle());
        task.setDescription(in.getDescription());
        task.setEstimatedDate(in.getEstimatedDate());
        task.setCreatedDate(LocalDate.now());
        task.setFinished(false);
        task.setTaskStatus(TaskStatus.ON_TIME);
        return task;
    }
}
