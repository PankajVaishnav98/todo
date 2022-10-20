package com.learning.java.todo.controller;


import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.AuthRequest;
import com.learning.java.todo.model.UserDto;
import com.learning.java.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) throws UserException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.authenticate(authRequest.getUserName(),authRequest.getPassword()));
    }
}
