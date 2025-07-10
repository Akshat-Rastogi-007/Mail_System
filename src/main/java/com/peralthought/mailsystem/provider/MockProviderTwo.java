package com.peralthought.mailsystem.provider;


import com.peralthought.mailsystem.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class MockProviderTwo implements EmailProvider{

    private final static Logger log = LoggerFactory.getLogger("email");

    @Override
    public String sendEmail(EmailRequest request) {

        log.info("MockProviderTwo: Attempting to send email to {}", request.getTo());

        try {
            if (Math.random() < 0.9) {
                log.info("MockProviderTwo: Email sent successfully to {}", request.getTo());
                return "SUCCESS";
            } else {
                log.warn("MockProviderTwe: Simulated failure - TIMEOUT");
                return "FAILURE_PROVIDER_DOWN";
            }
        } catch (Exception e) {
            log.error("MockProviderTwo: Unexpected error - {}", e.getMessage());
            return "FAILURE_UNKNOWN";
        }
    }
}
