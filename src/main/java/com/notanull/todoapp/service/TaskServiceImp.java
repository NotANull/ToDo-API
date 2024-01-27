package com.notanull.todoapp.todoApp.service;

import com.notanull.todoapp.todoApp.dto.request.TaskDto;
import com.notanull.todoapp.todoApp.dto.response.ResponseTask;
import com.notanull.todoapp.todoApp.exceptions.IdNotFoundException;
import com.notanull.todoapp.todoApp.mapper.TaskInDtoToTask;
import com.notanull.todoapp.todoApp.persistence.entity.Task;
import com.notanull.todoapp.todoApp.persistence.entity.TaskStatus;
import com.notanull.todoapp.todoApp.persistence.repository.ITaskRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImp implements ITaskService{

    private final ITaskRepository taskRepository;
    private final TaskInDtoToTask mapper;
    private final ModelMapper modelMapper;

    public TaskServiceImp(ITaskRepository taskRepository, TaskInDtoToTask mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = mapper.map(taskDto); //ModelMapper could be used
        this.taskRepository.save(task);
        return taskDto;
    }

    @Override
    public List<ResponseTask> findAll() {
        List<Task> taskList = this.taskRepository.findAll();
        List<ResponseTask> responseTaskList = new ArrayList<>();
        taskList.forEach(t -> responseTaskList.add(modelMapper.map(t, ResponseTask.class)));
        return responseTaskList;
    }

    @Override
    public List<ResponseTask> findAllTaskStatus(TaskStatus taskStatus) {
        List<Task> taskList = this.taskRepository.findAllByTaskStatus(taskStatus);
        List<ResponseTask> responseTaskList = new ArrayList<>();
        taskList.forEach(t -> responseTaskList.add(modelMapper.map(t, ResponseTask.class)));
        return responseTaskList;
    }

    @Transactional
    @Override
    public String updateTaskAsFinished(Long id) {
        if (this.taskRepository.findById(id).isEmpty()) {
            throw new IdNotFoundException("Id not found");
        }
        this.taskRepository.markTaskAsFinished(id);
        return "Task updated successfully";
    }

    @Override
    public String deleteById(Long id) {
        if (this.taskRepository.findById(id).isEmpty()) {
            throw new IdNotFoundException("Id not found");
        }
        this.taskRepository.deleteById(id);
        return "Task deleted successfully";
    }
}
