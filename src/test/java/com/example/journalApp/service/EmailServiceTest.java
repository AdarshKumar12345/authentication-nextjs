package com.example.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail(){
        emailService.sendEmail("nipunoraon26@gmail.com","Test Email from IntelliJ","Hello Adarsh This email was sent from Spring Boot test.");
    }

}

