package com.peralthought.mailsystem.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {

    private final Map<String, Long> lastSentMap = new ConcurrentHashMap<>();

    private static final long COOLDOWN_MS = 5000;

    public boolean isAllowed(String recipientEmail) {
        long currentTime = System.currentTimeMillis();
        Long lastSentTime = lastSentMap.get(recipientEmail);

        if (lastSentTime == null || (currentTime - lastSentTime) >= COOLDOWN_MS) {
            lastSentMap.put(recipientEmail, currentTime);
            return true;
        }

        return false;
    }

}
