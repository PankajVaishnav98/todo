package com.learning.java.todo.util;


import com.learning.java.todo.entity.User;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionUtil {

    @Autowired
    private UserRepository userRepository;

    public User getUserFromSession() throws UserException {

        String username;
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = user.getUsername();
        } catch (Exception ex) {
            throw new UserException("1002", "User authentication failed.");
        }
        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception ex) {
            throw new UserException("1003", String.format("User %s is not present.", username));
        }
        return user;
    }
}
