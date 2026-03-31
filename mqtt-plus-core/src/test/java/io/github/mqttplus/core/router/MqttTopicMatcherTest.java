package io.github.mqttplus.core.router;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MqttTopicMatcherTest {

    private final MqttTopicMatcher matcher = new MqttTopicMatcher();

    @Test
    void shouldMatchExactTopicAndWildcards() {
        assertTrue(matcher.matches("devices/1/status", "devices/1/status"));
        assertTrue(matcher.matches("devices/+/status", "devices/1/status"));
        assertTrue(matcher.matches("devices/#", "devices/a/b/status"));
    }

    @Test
    void shouldRejectNonMatchesAndSysTopicLeakage() {
        assertFalse(matcher.matches("devices/+/status", "devices/1/events"));
        assertFalse(matcher.matches("devices/#", "$SYS/broker/load"));
    }
}
