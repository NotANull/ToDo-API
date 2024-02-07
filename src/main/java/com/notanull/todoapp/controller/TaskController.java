package com.notanull.todoapp.controller;

import com.notanull.todoapp.dto.request.TaskDto;
import com.notanull.todoapp.persistence.entity.TaskStatus;
import com.notanull.todoapp.service.TaskServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImp taskService;

    public TaskController(TaskServiceImp taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDto taskDto) {
        return new ResponseEntity<>(this.taskService.createTask(taskDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTaskList() {
        return new ResponseEntity<>(this.taskService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTaskListByStatus(@PathVariable TaskStatus status) {
        return new ResponseEntity<>(this.taskService.findAllTaskStatus(status), HttpStatus.OK);
    }

    @PatchMapping("/mark_as_finished/{id}")
    public ResponseEntity<?> markAsFinished(@PathVariable Long id) {
        return new ResponseEntity<>(this.taskService.updateTaskAsFinished(id), HttpStatus.OK);
    }

    @PatchMapping("/extend_date/{id}")
    public ResponseEntity<?> extendEstimatedDate(@PathVariable Long id) {
        return new ResponseEntity<>(this.taskService.extendEstimatedDate(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(this.taskService.deleteById(id), HttpStatus.OK);
    }
}
