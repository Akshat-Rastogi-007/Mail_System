package com.peralthought.mailsystem.provider;

import com.peralthought.mailsystem.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MockProviderOne implements EmailProvider {

    private final static Logger log = LoggerFactory.getLogger("email");

    @Override
    public String sendEmail(EmailRequest request) {

        log.info("MockProviderOne: Attempting to send email to {}", request.getTo());

        try {
            if (Math.random() < 0.7) {
                log.info("MockProviderOne: Email sent successfully to {}", request.getTo());
                return "SUCCESS";
            } else {
                log.warn("MockProviderOne: Simulated failure - TIMEOUT");
                return "FAILURE_TIMEOUT";
            }
        } catch (Exception e) {
            log.error("MockProviderOne: Unexpected error - {}", e.getMessage());
            return "FAILURE_UNKNOWN";
        }
    }
}
