package com.learning.java.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateException extends  Exception{
    private String errorCode;
    private  String description;
}