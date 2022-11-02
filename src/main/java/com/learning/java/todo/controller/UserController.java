package com.learning.java.todo.controller;


import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.AuthRequest;
import com.learning.java.todo.model.UserDto;
import com.learning.java.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) throws UserException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.authenticate(authRequest.getUsername(),authRequest.getPassword()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) throws UserException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.OK).body(userService.logOut(token));
    }
}
