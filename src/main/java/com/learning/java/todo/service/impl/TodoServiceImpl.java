package com.learning.java.todo.service.impl;

import com.learning.java.todo.entity.Todo;
import com.learning.java.todo.entity.User;
import com.learning.java.todo.exception.DateException;
import com.learning.java.todo.exception.TodoException;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.TodoDto;
import com.learning.java.todo.repository.TodoRepository;
import com.learning.java.todo.service.TodoService;
import com.learning.java.todo.util.DateFormatterValidator;
import com.learning.java.todo.util.JwtUtil;
import com.learning.java.todo.util.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private static final Logger logger = LogManager.getLogger(TodoServiceImpl.class);

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
        Date targetDate = DateFormatterValidator.validateAndFormatDate(todoDto.getTargetDate());
        Todo todo = Todo.builder().title(todoDto.getTitle()).description(todoDto.getDescription()).targetDate(targetDate).user(sessionUtil.getUserFromSession()).build();
        try {
            todoRepository.save(todo);
        } catch (DataIntegrityViolationException de) {
            logger.info("Exception while creating todo with title : {}", todo.getTitle());
            if (de.getMessage().contains("constraint [todo")) {
                logger.info(String.format("Todo with title %s already available", todo.getTitle()));
                throw new UserException("2004", String.format("Todo with title %s already available", todo.getTitle()));
            }
            throw new UserException("2001", "Todo creation failed.");
        } catch (Exception ex) {
            logger.info("Exception while creating todo with title : {}, exception is : {}", todo.getTitle(), ex.getMessage());
            throw new TodoException("2001", "Todo creation failed.");
        }
        return String.format("Todo %s created, target time is %s", todo.getTitle(), todo.getTargetDate());
    }

    @Override
    public List<TodoDto> getTodoList() throws UserException, TodoException {
        try {
            List<Todo> todoList = todoRepository.findByUserId(sessionUtil.getUserFromSession().getId()).orElseThrow(() -> new TodoException("2003", "Failed to fetch todo list."));
            return todoList.stream().map(todoItem -> modelMapper.map(todoItem, TodoDto.class)).collect(Collectors.toList());
        } catch (Exception ex) {
            logger.info("Exception while fetching todo list : {}",ex.getMessage());
            throw new TodoException("2003", "Failed to fetch todo list.");
        }
    }

    @Override
    public String deleteTodo(String title) throws UserException, TodoException {
        User user = sessionUtil.getUserFromSession();
        try {
            Todo todoToDelete = todoRepository.findByUserIdAndTitle(user.getId(), title);
            todoRepository.delete(todoToDelete);
        } catch (Exception ex) {
            logger.info("Exception while deleting todo with title : {}, {}",title ,ex.getMessage());
            throw new TodoException("2005", String.format("Failed to delete Todo with title %s.", title));
        }
        return "Successfully Deleted";
    }

    @Override
    public String updateTodo(TodoDto todoDto) throws UserException, DateException, TodoException {
        Date targetDate = DateFormatterValidator.validateAndFormatDate(todoDto.getTargetDate());
        User user = sessionUtil.getUserFromSession();
        try {
            Todo todoToUpdate = todoRepository.findByUserIdAndTitle(user.getId(), todoDto.getTitle());
            todoToUpdate.setDescription(todoDto.getDescription());
            todoToUpdate.setTargetDate(targetDate);
            todoRepository.save(todoToUpdate);
        } catch (Exception ex) {
            logger.info("Exception while updating todo : {}",ex.getMessage());
            throw new TodoException("2006", String.format("Failed to update Todo with title %s.", todoDto.getTitle()));
        }
        return "Successfully Updated";
    }
}
