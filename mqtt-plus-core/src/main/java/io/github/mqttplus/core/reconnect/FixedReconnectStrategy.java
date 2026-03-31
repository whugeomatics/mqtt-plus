package io.github.mqttplus.core.reconnect;

import java.time.Duration;

public final class FixedReconnectStrategy implements ReconnectStrategy {

    private final Duration delay;

    public FixedReconnectStrategy(Duration delay) {
        if (delay == null || delay.isNegative() || delay.isZero()) {
            throw new IllegalArgumentException("delay must be greater than zero");
        }
        this.delay = delay;
    }

    @Override
    public Duration nextDelay(int attempt) {
        return delay;
    }
}
