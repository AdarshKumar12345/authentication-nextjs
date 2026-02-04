package com.example.journalApp.controller;

import com.example.journalApp.api.responce.WeatherResponce;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import com.example.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    @GetMapping
    public ResponseEntity<?> findAllUser(){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }
        WeatherResponce weatherResponce = weatherService.getWeather("MUMBAI");
        if( weatherResponce== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>("Hi" + authentication.getName() +" todays weather fells like" +weatherResponce.getCurrent().getFeelslike(), HttpStatus.OK);


    }



    @GetMapping("/{userName}")
    public ResponseEntity<?> findUserById (@PathVariable String userName){
        User myUser = userService.findByUserName(userName);
        if( myUser != null){
            return new ResponseEntity<>(myUser , HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
    @PutMapping
    public ResponseEntity<?> EditUser(@RequestBody User editUser){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }
        String userName = authentication.getName();

        User oldUser = userService.findByUserName(userName);
        if(oldUser!= null){
            oldUser.setUserName(editUser.getUserName());
            oldUser.setPassword(editUser.getPassword());
            userService.saveNewUsers(oldUser);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }
    @DeleteMapping
    public ResponseEntity<?> DeleteUser(){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);

    }




}
