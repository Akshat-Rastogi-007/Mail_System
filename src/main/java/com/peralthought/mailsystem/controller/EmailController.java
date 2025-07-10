package com.peralthought.mailsystem.controller;

import com.peralthought.mailsystem.dto.EmailRequest;
import com.peralthought.mailsystem.model.EmailStatus;
import com.peralthought.mailsystem.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        EmailStatus status = emailService.sendEmail(request);

        switch (status) {
            case SUCCESS:
                return ResponseEntity.ok("Email sent successfully");
                case RATE_LIMITED:
                    return ResponseEntity.status(429).body("Rate limit exceeded");
                    case FAILED:
                        return ResponseEntity.status(500).body("All providers failed");
                        default:
                            return ResponseEntity.status(500).body("Unexpected error");
        }
    }

    @GetMapping("/getStatus")
    public ResponseEntity<?> getStatus(@RequestParam String key) {
        EmailStatus emailStatus = emailService.getEmailStatus(key);
        switch (emailStatus) {
            case SUCCESS:
                return ResponseEntity.ok(emailStatus);
                case RATE_LIMITED:
                    return ResponseEntity.status(429).body("Rate limit exceeded");
                    case FAILED:
                        return ResponseEntity.status(500).body("All providers failed");
                        default:
                            return ResponseEntity.status(500).body("Unexpected error");
        }
    }
}
