package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping( "/health-check")
    public String healthCheck(){
        return "ok";
    }
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User myUser){
        try{
            userService.saveNewUsers(myUser);
            return new ResponseEntity<>(myUser , HttpStatus.CREATED);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }



}
