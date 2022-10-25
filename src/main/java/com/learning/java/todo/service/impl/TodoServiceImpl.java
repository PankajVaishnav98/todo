package com.learning.java.todo.service.impl;

import com.learning.java.todo.entity.Todo;
import com.learning.java.todo.exception.DateException;
import com.learning.java.todo.exception.TodoException;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.TodoDto;
import com.learning.java.todo.repository.TodoRepository;
import com.learning.java.todo.service.TodoService;
import com.learning.java.todo.util.JwtUtil;
import com.learning.java.todo.util.SessionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public String createTodo(TodoDto todoDto) throws TodoException, UserException, DateException {
        Date targetDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);
            targetDate  = formatter.parse(todoDto.getTargetDate());
            if(targetDate.before(new Date())){
                throw  new DateException("3002","Target Date can not be a past date.");
            }
        }catch (ParseException ex){
            throw  new DateException("3001","Invalid Target Date Provided.");
        }


        Todo todo = Todo.builder().title(todoDto.getTitle())
                .description(todoDto.getDescription())
                .targetDate(targetDate)
                .user(sessionUtil.getUserFromSession()).build();
        try {
            todoRepository.save(todo);
        } catch (Exception ex) {
            throw new TodoException("2001", "Todo creation failed");
        }
        return String.format("Todo %s created, target time is %s", todo.getTitle(), todo.getTargetDate());
    }

    @Override
    public List<TodoDto> getTodoList() throws UserException, TodoException {
        try {
            List<Todo> todoList = todoRepository.findByUserId(sessionUtil.getUserFromSession().getId()).orElseThrow(() -> new TodoException("2003", "Failed to fetch todo list."));
            return todoList.stream().map(todoItem -> modelMapper.map(todoItem, TodoDto.class)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new TodoException("2003", "Failed to fetch todo list.");
        }
    }

    @Override
    public String removeTodo() {
        return null;
    }

    @Override
    public String updateTodo() {
        return null;
    }


}
