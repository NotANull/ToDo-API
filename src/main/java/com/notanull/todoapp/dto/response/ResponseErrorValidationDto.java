package com.notanull.todoapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorValidationDto {

    private int status;
    private HashMap<String, String> errors;
}
