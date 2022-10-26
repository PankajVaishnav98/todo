package com.learning.java.todo.service.impl;

import com.learning.java.todo.entity.User;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.UserDto;
import com.learning.java.todo.repository.UserRepository;
import com.learning.java.todo.service.UserService;
import com.learning.java.todo.util.JwtUtil;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String createUser(UserDto userDto) throws UserException {
        User user = User.builder().username(userDto.getUsername()).password(passwordEncoder.encode(userDto.getPassword())).build();
        try {
            userRepo.save(user);
            logger.info("User created successfully with username : {}", user.getUsername());
        }catch(DataIntegrityViolationException de){
            logger.info("Exception while creating user with username : {}", user.getUsername());
            if(de.getMessage().contains("constraint [user"))
                throw new UserException("1004",String.format("Username %s already taken",user.getUsername()));
            throw new UserException("1001","User creation failed.");
        }
        catch (Exception ex){
            logger.info("Exception while creating user with username : {}", user.getUsername());
            throw new UserException("1001","User creation failed.");

        }
        return String.format("User Created Successfully with username : %s", user.getUsername()) ;
    }

    @Override
    public String updatePassword(String passwordString) {
        return null;
    }

    @Override
    public String logOut(String token) {
        Date expirationTime = jwtUtil.setExpiration(token);
        if(expirationTime.before(new Date())){
            return "logged out";
        }
        else return "failed to log out";
    }

    @Override
    public String authenticate(String username, String password) throws UserException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception ex) {
            logger.info("Exception while logging in user with username : {}", username);
            throw new UserException("1002","User authentication failed.");
        }
        return jwtUtil.generateToken(username);
    }
}
