package com.learning.java.todo.service;

import com.learning.java.todo.entity.User;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.UserDto;

public interface UserService {
    public String createUser(UserDto userDto);
    public String updatePassword(String passwordString);
    public String authenticate(String  userName, String password) throws UserException;
}
