package com.peralthought.mailsystem.util;

import com.peralthought.mailsystem.model.EmailStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmailStatusStore {


    private final ConcurrentHashMap<String, EmailStatus> statusMap = new ConcurrentHashMap<>();

    public void updateStatus(String key, EmailStatus status) {
        statusMap.put(key, status);
    }

    public EmailStatus getStatus(String key) {
        return statusMap.getOrDefault(key, EmailStatus.FAILED);
    }

    public boolean isAlreadySuccessful(String key) {
        return EmailStatus.SUCCESS.equals(statusMap.get(key));
    }
}
