package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveNewUsers(User user){
        user.setPassword(Objects.requireNonNull(passwordEncoder.encode(user.getPassword())));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);

    }
    public void saveAdmin(User user){
        user.setPassword(Objects.requireNonNull(passwordEncoder.encode(user.getPassword())));
        user.setRoles(Arrays.asList("USER" , "ADMIN"));
        userRepository.save(user);

    }
    public void saveUser( User user){
        userRepository.save(user);

    }
    public List<User> getAllUser(){
        return userRepository.findAll();

    }
    public Optional<User> findUserById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteByUserName  (String userName){
        userRepository.deleteByUserName(userName);

    }
    public User findByUserName (String userName){
        return userRepository.findByUserName(userName);

    }



}
