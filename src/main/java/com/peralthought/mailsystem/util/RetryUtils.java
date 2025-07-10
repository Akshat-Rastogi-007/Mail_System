package com.peralthought.mailsystem.util;

import java.util.function.Supplier;

public class RetryUtils {

    public static String retryWithBackoff(Supplier<String> task, int maxAttempts, long baseDelayMillis) {

        int attempt = 0;
        long delay = baseDelayMillis;

        while (attempt < maxAttempts) {
            String result = task.get();

            if ("SUCCESS".equalsIgnoreCase(result)) {
                return "SUCCESS";
            }

            attempt++;

            if (attempt < maxAttempts) {
                try {
                    Thread.sleep(delay);
                    delay *= 2;
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    return "FAILURE_INTERRUPTED";
                }
            }
        }

        return "FAILURE_RETRY_EXCEEDED";
    }
}
