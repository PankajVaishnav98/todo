package com.learning.java.todo.controller;


import com.learning.java.todo.exception.DateException;
import com.learning.java.todo.exception.TodoException;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.TodoDto;
import com.learning.java.todo.repository.TodoRepository;
import com.learning.java.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping()
    public ResponseEntity<String> createTodo(@RequestBody TodoDto todoDto) throws UserException, TodoException, DateException {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoDto));
    }

    @GetMapping()
    public ResponseEntity<List<TodoDto>> getTodoList() throws UserException, TodoException {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoList());
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteTodo(@RequestParam("title") String title) throws UserException, TodoException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todoService.deleteTodo(title));
    }

    @PutMapping()
    public ResponseEntity<String> updateTodo(@RequestBody TodoDto todoDto) throws UserException, TodoException, DateException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todoService.updateTodo(todoDto));
    }

}
