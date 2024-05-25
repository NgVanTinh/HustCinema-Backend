package com.hustcinema.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hustcinema.backend.service.UserService;

import jakarta.validation.Valid;

import com.hustcinema.backend.dto.request.UserCreationRequest;
import com.hustcinema.backend.dto.request.UserUpdateRequest;
import com.hustcinema.backend.dto.respond.UserRespond;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.UserRepository;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired 
    UserRepository userRepository;
    @PostMapping("/")
    public User newUser(@RequestBody @Valid UserCreationRequest newUser) {
        return userService.createNewUser(newUser);
    }

    @GetMapping("/")
    public List<UserRespond> getAllUser() {
        return userService.allUser();
    }

    @GetMapping("/my-infor")
    public UserRespond getUserInfor(){
        return userService.getMyInfo();
    }
    @PutMapping("/")
    public User updatUser(@RequestBody UserUpdateRequest newUser){
        return userService.updateUser(newUser);
    }

    @DeleteMapping("/{id}")
    public String deleUser(@PathVariable String id){
        return userService.deleteUser(id);
    } 
    
    

}
