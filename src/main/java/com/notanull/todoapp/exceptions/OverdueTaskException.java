package com.notanull.todoapp.exceptions;

public class OverdueTaskException extends RuntimeException{

    public OverdueTaskException(String message) {
        super(message);
    }
}
