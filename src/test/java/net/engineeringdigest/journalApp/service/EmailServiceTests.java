package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("shyam.violator@slmail.me", "Test Mail", "Hello");
//        emailService.sendEmail("carnagtion_duchess348@mail.me",
//                "Testing Java mail sender",
//                "hi, app kaise hai?");
    }
}
