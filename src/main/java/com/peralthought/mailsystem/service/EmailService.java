package com.peralthought.mailsystem.service;

import com.peralthought.mailsystem.dto.EmailRequest;
import com.peralthought.mailsystem.model.EmailStatus;
import com.peralthought.mailsystem.provider.EmailProvider;
import com.peralthought.mailsystem.util.EmailStatusStore;
import com.peralthought.mailsystem.util.RateLimiter;
import com.peralthought.mailsystem.util.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {


    private final List<EmailProvider> providers;
    private final EmailStatusStore statusStore;
    private final RateLimiter rateLimiter;
    private final static Logger log = LoggerFactory.getLogger("email");

    public EmailService(List<EmailProvider> providers, EmailStatusStore statusStore, RateLimiter rateLimiter) {
        this.providers = providers;
        this.statusStore = statusStore;
        this.rateLimiter = rateLimiter;
    }

    public EmailStatus sendEmail(EmailRequest request) {

        String key = request.getKey();

        if (statusStore.isAlreadySuccessful(key)) {
            log.warn("Email already sent for key {}", key);
            return EmailStatus.SUCCESS;
        }

        if (!rateLimiter.isAllowed(request.getTo())) {
            log.warn("Rate limit exceeded for key {}", key);
            statusStore.updateStatus(key, EmailStatus.RATE_LIMITED);
            return EmailStatus.RATE_LIMITED;
        }

        for (EmailProvider provider : providers) {
            String result = RetryUtils.retryWithBackoff(() -> provider.sendEmail(request), 3, 1000);

            if ("SUCCESS".equalsIgnoreCase(result)) {
                log.info("Successfully sent email for key {}", key);
                statusStore.updateStatus(key, EmailStatus.SUCCESS);
                return EmailStatus.SUCCESS;
            }

        }

        log.warn("Email not sent for key {}", key);
        statusStore.updateStatus(key, EmailStatus.FAILED);
        return EmailStatus.FAILED;
    }

    public EmailStatus getEmailStatus(String key) {
        log.info("Retrieving email status for key {}", key);
        return statusStore.getStatus(key);
    }

}
