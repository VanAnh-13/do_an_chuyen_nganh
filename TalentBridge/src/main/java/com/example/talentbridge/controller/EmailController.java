package com.example.talentbridge.controller;

import lombok.RequiredArgsConstructor;
import com.example.talentbridge.service.EmailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "spring.mail.host",
        matchIfMissing = false
)
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/emails/job-recommendation/{email}")
    public ResponseEntity<String> sendJobMail(@PathVariable String email) {
        try {
            emailService.sendJobNotificationManually(email);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(500)
                    .body("Email sending failed: " + ex.getMessage());
        }
    }
}
