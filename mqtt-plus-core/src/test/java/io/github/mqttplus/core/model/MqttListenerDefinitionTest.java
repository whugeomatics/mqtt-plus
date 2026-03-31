package io.github.mqttplus.core.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttListenerDefinitionTest {

    @Test
    void shouldStoreListenerMetadata() throws NoSuchMethodException {
        SampleHandler bean = new SampleHandler();
        Method method = SampleHandler.class.getDeclaredMethod("handle", String.class);
        MqttListenerDefinition definition = new MqttListenerDefinition(
                "sampleHandler",
                bean,
                method,
                "primary",
                List.of("devices/+/status"),
                1,
                String.class
        );

        assertEquals("sampleHandler", definition.getBeanName());
        assertEquals(bean, definition.getBean());
        assertEquals(method, definition.getMethod());
        assertEquals("primary", definition.getBroker());
        assertEquals(List.of("devices/+/status"), definition.getTopics());
        assertEquals(1, definition.getQos());
        assertEquals(String.class, definition.getPayloadType());
    }

    static final class SampleHandler {
        void handle(String payload) {
        }
    }
}
