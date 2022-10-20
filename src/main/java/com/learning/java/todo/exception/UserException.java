package com.learning.java.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends Exception{
    private String errorCode;
    private String description;
}
