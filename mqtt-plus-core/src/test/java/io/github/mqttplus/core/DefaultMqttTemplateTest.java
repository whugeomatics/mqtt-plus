package io.github.mqttplus.core;

import io.github.mqttplus.core.adapter.DefaultMqttClientAdapterRegistry;
import io.github.mqttplus.core.adapter.MqttClientAdapter;
import io.github.mqttplus.core.adapter.MqttConnectionListener;
import io.github.mqttplus.core.model.MqttBrokerDefinition;
import io.github.mqttplus.core.model.ThreadPoolConfig;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultMqttTemplateTest {

    @Test
    void shouldDelegatePublishToResolvedAdapter() {
        DefaultMqttClientAdapterRegistry registry = new DefaultMqttClientAdapterRegistry();
        RecordingAdapter adapter = new RecordingAdapter("primary");
        registry.register(adapter);
        DefaultMqttTemplate template = new DefaultMqttTemplate(registry);

        template.publish("primary", "devices/1/status", "ok");

        assertEquals("devices/1/status", adapter.lastTopic);
        assertEquals("ok", adapter.lastPayload);
    }

    @Test
    void shouldFailWhenBrokerIsMissing() {
        DefaultMqttTemplate template = new DefaultMqttTemplate(new DefaultMqttClientAdapterRegistry());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> template.publish("missing", "topic", "payload"));

        assertEquals("No adapter registered for broker: missing", exception.getMessage());
    }

    static final class RecordingAdapter implements MqttClientAdapter {
        private final MqttBrokerDefinition definition;
        private String lastTopic;
        private Object lastPayload;

        RecordingAdapter(String brokerId) {
            this.definition = MqttBrokerDefinition.builder()
                    .brokerId(brokerId)
                    .host("127.0.0.1")
                    .clientId("template-test")
                    .inboundThreadPool(ThreadPoolConfig.builder().build())
                    .build();
        }

        @Override
        public String getBrokerId() {
            return definition.getBrokerId();
        }

        @Override
        public MqttBrokerDefinition getBrokerDefinition() {
            return definition;
        }

        @Override
        public void connect() {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public void subscribe(String topic, int qos) {
        }

        @Override
        public void unsubscribe(String topic) {
        }

        @Override
        public void publish(String topic, Object payload) {
            this.lastTopic = topic;
            this.lastPayload = payload;
        }

        @Override
        public CompletableFuture<Void> publishAsync(String topic, Object payload) {
            publish(topic, payload);
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public boolean supportsManualAck() {
            return false;
        }

        @Override
        public void addConnectionListener(MqttConnectionListener listener) {
        }
    }
}