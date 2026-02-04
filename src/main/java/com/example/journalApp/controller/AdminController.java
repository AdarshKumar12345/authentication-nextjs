package com.example.journalApp.controller;


import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser(){
        List<User> allUser = userService.getAllUser();
        if( allUser == null || allUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(allUser, HttpStatus.OK);


    }
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user ){
        try {
            userService.saveAdmin(user);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    



}
