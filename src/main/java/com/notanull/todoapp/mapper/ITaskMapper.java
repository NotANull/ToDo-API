package com.notanull.todoapp.todoApp.mapper;

public interface ITaskMapper <I, O>{
    O map(I in); //return Output, parameter Input
}
