package io.github.mqttplus.spring.invocation;

import io.github.mqttplus.core.annotation.MqttTopic;
import io.github.mqttplus.core.model.MqttHeaders;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttListenerMethodArgumentResolverTest {

    @Test
    void shouldResolvePayloadTopicHeadersAndRawPayload() throws Exception {
        MqttListenerMethodArgumentResolver resolver = new MqttListenerMethodArgumentResolver();
        Method method = SampleHandler.class.getDeclaredMethod("handle", String.class, String.class, MqttHeaders.class, byte[].class);
        MqttHeaders headers = new MqttHeaders(Map.of("qos", 1));
        byte[] raw = new byte[]{1, 2, 3};

        Object[] args = resolver.resolveArguments(method, "payload", raw, "devices/1/status", headers);

        assertEquals("payload", args[0]);
        assertEquals("devices/1/status", args[1]);
        assertEquals(headers, args[2]);
        assertArrayEquals(raw, (byte[]) args[3]);
    }

    static final class SampleHandler {
        void handle(String payload, @MqttTopic String topic, MqttHeaders headers, byte[] rawPayload) {
        }
    }
}
