package com.peralthought.mailsystem.service;

import com.peralthought.mailsystem.dto.EmailRequest;
import com.peralthought.mailsystem.model.EmailStatus;
import com.peralthought.mailsystem.provider.EmailProvider;
import com.peralthought.mailsystem.util.EmailStatusStore;
import com.peralthought.mailsystem.util.RateLimiter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailServiceTest {

    @Test
    void shouldSendSuccessfullyWithFirstProvider() {
        EmailProvider provider1 = request -> "SUCCESS";
        EmailProvider provider2 = request -> "FAILURE";

        EmailService service = new EmailService(List.of(provider1, provider2),
                new EmailStatusStore(), new RateLimiter());

        EmailRequest request = new EmailRequest("id1", "to@example.com", "Subject", "Body");
        EmailStatus result = service.sendEmail(request);

        assertEquals(EmailStatus.SUCCESS, result);
    }

    @Test
    void shouldFallbackToSecondProvider() {
        EmailProvider provider1 = request -> "FAILURE";
        EmailProvider provider2 = request -> "SUCCESS";

        EmailService service = new EmailService(List.of(provider1, provider2),
                new EmailStatusStore(), new RateLimiter());

        EmailRequest request = new EmailRequest("id2", "to@example.com", "Subject", "Body");
        EmailStatus result = service.sendEmail(request);

        assertEquals(EmailStatus.SUCCESS, result);
    }

    @Test
    void shouldRespectRateLimiting() {
        EmailProvider provider = request -> "SUCCESS";

        RateLimiter limiter = new RateLimiter();
        EmailService service = new EmailService(List.of(provider),
                new EmailStatusStore(), limiter);

        EmailRequest request1 = new EmailRequest("id3", "to@example.com", "Subject", "Body");
        EmailRequest request2 = new EmailRequest("id4", "to@example.com", "Subject", "Body");

        assertEquals(EmailStatus.SUCCESS, service.sendEmail(request1));
        assertEquals(EmailStatus.RATE_LIMITED, service.sendEmail(request2));
    }

    @Test
    void shouldRespectIdempotency() {
        EmailProvider provider = request -> "SUCCESS";
        EmailStatusStore store = new EmailStatusStore();
        EmailService service = new EmailService(List.of(provider), store, new RateLimiter());

        EmailRequest request = new EmailRequest("idempotent-key", "to@example.com", "Subject", "Body");

        EmailStatus firstAttempt = service.sendEmail(request);
        EmailStatus secondAttempt = service.sendEmail(request);

        assertEquals(EmailStatus.SUCCESS, firstAttempt);
        assertEquals(EmailStatus.SUCCESS, secondAttempt);
    }
}
