package io.github.mqttplus.core.reconnect;

import java.time.Duration;

public final class ExponentialBackoffReconnectStrategy implements ReconnectStrategy {

    private final Duration initialDelay;
    private final Duration maxDelay;

    public ExponentialBackoffReconnectStrategy(Duration initialDelay, Duration maxDelay) {
        if (initialDelay == null || initialDelay.isNegative() || initialDelay.isZero()) {
            throw new IllegalArgumentException("initialDelay must be greater than zero");
        }
        if (maxDelay == null || maxDelay.compareTo(initialDelay) < 0) {
            throw new IllegalArgumentException("maxDelay must be greater than or equal to initialDelay");
        }
        this.initialDelay = initialDelay;
        this.maxDelay = maxDelay;
    }

    @Override
    public Duration nextDelay(int attempt) {
        int safeAttempt = Math.max(attempt, 1);
        long multiplier = 1L << Math.min(safeAttempt - 1, 30);
        long nextMillis = initialDelay.toMillis() * multiplier;
        return Duration.ofMillis(Math.min(nextMillis, maxDelay.toMillis()));
    }
}
