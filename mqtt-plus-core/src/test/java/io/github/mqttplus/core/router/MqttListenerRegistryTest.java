package io.github.mqttplus.core.router;

import io.github.mqttplus.core.model.MqttListenerDefinition;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttListenerRegistryTest {

    @Test
    void shouldResolveByTopicAndBrokerWildcard() throws NoSuchMethodException {
        MqttListenerRegistry registry = new MqttListenerRegistry();
        SampleHandler bean = new SampleHandler();
        Method method = SampleHandler.class.getDeclaredMethod("handle", String.class);
        registry.register(new MqttListenerDefinition("a", bean, method, "primary", List.of("devices/+/status"), 1, String.class));
        registry.register(new MqttListenerDefinition("b", bean, method, "*", List.of("devices/#"), 0, String.class));

        assertEquals(2, registry.resolve("primary", "devices/1/status").size());
        assertEquals(2, registry.resolveByBroker("primary").size());
        assertEquals(1, registry.resolve("secondary", "devices/1/status").size());
    }

    static final class SampleHandler {
        void handle(String payload) {
        }
    }
}
