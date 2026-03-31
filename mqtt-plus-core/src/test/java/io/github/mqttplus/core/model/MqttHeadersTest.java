package io.github.mqttplus.core.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MqttHeadersTest {

    @Test
    void shouldExposeHeadersWithoutAllowingMutation() {
        MqttHeaders headers = new MqttHeaders(Map.of("contentType", "application/json", "qos", 1));

        assertEquals("application/json", headers.getAsString("contentType"));
        assertEquals(1, headers.get("qos"));
        assertNull(headers.get("missing"));
        assertThrows(UnsupportedOperationException.class, () -> headers.asMap().put("foo", "bar"));
    }
}
