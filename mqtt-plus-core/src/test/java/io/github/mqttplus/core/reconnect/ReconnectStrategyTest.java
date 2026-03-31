package io.github.mqttplus.core.reconnect;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReconnectStrategyTest {

    @Test
    void shouldUseFixedDelayForAllAttempts() {
        ReconnectStrategy strategy = new FixedReconnectStrategy(Duration.ofSeconds(5));

        assertEquals(Duration.ofSeconds(5), strategy.nextDelay(1));
        assertEquals(Duration.ofSeconds(5), strategy.nextDelay(4));
    }

    @Test
    void shouldCapExponentialDelayAtMaximum() {
        ReconnectStrategy strategy = new ExponentialBackoffReconnectStrategy(Duration.ofSeconds(1), Duration.ofSeconds(8));

        assertEquals(Duration.ofSeconds(1), strategy.nextDelay(1));
        assertEquals(Duration.ofSeconds(2), strategy.nextDelay(2));
        assertEquals(Duration.ofSeconds(4), strategy.nextDelay(3));
        assertEquals(Duration.ofSeconds(8), strategy.nextDelay(4));
        assertEquals(Duration.ofSeconds(8), strategy.nextDelay(8));
    }
}
