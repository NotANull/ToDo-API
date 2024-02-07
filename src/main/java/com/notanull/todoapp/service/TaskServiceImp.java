package com.notanull.todoapp.service;

import com.notanull.todoapp.dto.request.TaskDto;
import com.notanull.todoapp.dto.response.ResponseTask;
import com.notanull.todoapp.exceptions.IdNotFoundException;
import com.notanull.todoapp.exceptions.OverdueTaskException;
import com.notanull.todoapp.mapper.TaskInDtoToTask;
import com.notanull.todoapp.persistence.entity.Task;
import com.notanull.todoapp.persistence.entity.TaskStatus;
import com.notanull.todoapp.persistence.repository.ITaskRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements ITaskService {

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
        Optional<Task> task = this.taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new IdNotFoundException("Id not found");
        }
        if (task.get().getTaskStatus().equals(TaskStatus.LATE)) {
            throw new OverdueTaskException("You cannot change the task status. Request an extension for the estimated time to complete it");
        }
        this.taskRepository.markTaskAsFinished(id);
        this.taskRepository.updateTaskStatus(id, 2);
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


    //Corregir cuando haya fecha año/12/día
    @Transactional
    @Override
    public String extendEstimatedDate(Long id) {
        if (this.taskRepository.findById(id).isEmpty()){
            return null;
        }
        this.taskRepository.updateTaskStatus(id, 0);
        this.taskRepository.updateEstimatedDate(id, LocalDate.now().plusMonths(1));
        return "The estimated date has been extended for one more month, please complete your assignment";
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") //Midnight
    public void setEstimatedDateAsLate() {
        if (!this.taskRepository.findAll().isEmpty()) {
            List<Task> listFiltered = this.taskRepository.findAll().stream()
                    .filter(t -> LocalDate.now().isAfter(t.getEstimatedDate()) && (!t.isFinished()))
                    .toList();
            if (!listFiltered.isEmpty()) {
                listFiltered.forEach(t -> this.taskRepository.updateTaskStatus(t.getId(), 1));
            }
        }
    }
    
}
