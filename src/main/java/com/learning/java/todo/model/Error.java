package com.learning.java.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Error {
    private String errorCode;
    private String description;
}
