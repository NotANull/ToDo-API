package com.notanull.todoapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @Size(min = 3, message = "The title must have at least 3 characters")
    @NotEmpty(message = "The title must not be left blank")
    private String title;
    private String description;
    private LocalDate estimatedDate;
}
