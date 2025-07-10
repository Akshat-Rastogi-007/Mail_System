package com.peralthought.mailsystem.provider;

import com.peralthought.mailsystem.dto.EmailRequest;

public interface EmailProvider {
    String sendEmail(EmailRequest request);
}
