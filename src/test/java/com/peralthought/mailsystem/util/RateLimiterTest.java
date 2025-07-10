package com.peralthought.mailsystem.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimiterTest {

    @Test
    public void shouldAllowInitialRequest() {
        RateLimiter limiter = new RateLimiter();
        assertTrue(limiter.isAllowed("user@example.com"));
    }

    @Test
    public void shouldBlockFrequentRequests() {
        RateLimiter limiter = new RateLimiter();
        limiter.isAllowed("user@example.com");
        assertFalse(limiter.isAllowed("user@example.com"));
    }

    @Test
    public void shouldAllowAfterTimeElapsed() throws InterruptedException {
        RateLimiter limiter = new RateLimiter();
        limiter.isAllowed("user@example.com");
        Thread.sleep(5100); // wait slightly over 5s
        assertTrue(limiter.isAllowed("user@example.com"));

    }
}
