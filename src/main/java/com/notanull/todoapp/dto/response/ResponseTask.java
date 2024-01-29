package com.notanull.todoapp.dto.response;

import com.notanull.todoapp.persistence.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTask {

    private String title;
    private String description;
    private LocalDate createdDate;
    private LocalDate estimatedDate;
    private boolean finished;
    private TaskStatus taskStatus;
}
