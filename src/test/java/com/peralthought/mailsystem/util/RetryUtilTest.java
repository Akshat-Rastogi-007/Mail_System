package com.peralthought.mailsystem.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetryUtilTest {

    @Test
    public void shouldSucceedOnFirstAttempt() {
        String result = RetryUtils.retryWithBackoff(() -> "SUCCESS", 3, 100);
        assertEquals("SUCCESS", result);
    }

    @Test
    public void shouldFailAfterAllAttempts() {
        String result = RetryUtils.retryWithBackoff(() -> "FAILURE", 3, 100);
        assertEquals("FAILURE_RETRY_EXCEEDED", result);
    }

    @Test
    public void shouldSucceedOnThirdAttempt() {
        final int[] count = {0};
        String result = RetryUtils.retryWithBackoff(() -> {
            return ++count[0] == 3 ? "SUCCESS" : "FAILURE";
        }, 3, 10); // Lower delay for tests
        assertEquals("SUCCESS", result);
        assertEquals(3, count[0]);
    }
}
