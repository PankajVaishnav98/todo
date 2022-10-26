package com.learning.java.todo.service;

import com.learning.java.todo.exception.DateException;
import com.learning.java.todo.exception.TodoException;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.TodoDto;

import java.util.List;

public interface TodoService {

    public String createTodo(TodoDto todoDto) throws TodoException, UserException, DateException;
    public List<TodoDto> getTodoList() throws UserException, TodoException;
    public String deleteTodo(String title) throws UserException, TodoException;
    public String updateTodo(TodoDto todoDto) throws UserException, DateException, TodoException;

}
