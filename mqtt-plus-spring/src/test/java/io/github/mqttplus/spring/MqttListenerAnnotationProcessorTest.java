package io.github.mqttplus.spring;

import io.github.mqttplus.core.annotation.MqttListener;
import io.github.mqttplus.core.router.MqttListenerRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttListenerAnnotationProcessorTest {

    @Test
    void shouldRegisterSingleAndWildcardBrokerListeners() {
        MqttListenerRegistry registry = new MqttListenerRegistry();
        MqttListenerAnnotationProcessor processor = new MqttListenerAnnotationProcessor(registry);

        processor.postProcessAfterInitialization(new SampleBean(), "sampleBean");

        assertEquals(2, registry.resolveByBroker("primary").size());
        assertEquals(1, registry.resolveByBroker("secondary").size());
        assertEquals(2, registry.resolve("primary", "devices/1/status").size());
    }

    static final class SampleBean {
        @MqttListener(broker = "primary", topics = {"devices/+/status"}, qos = 1, payloadType = String.class)
        void onPrimary(String payload) {
        }

        @MqttListener(broker = "*", topics = {"devices/#"}, payloadType = String.class)
        void onAll(String payload) {
        }
    }
}
