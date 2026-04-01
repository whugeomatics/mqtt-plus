package io.github.mqttplus.core;

import io.github.mqttplus.core.adapter.MqttClientAdapter;
import io.github.mqttplus.core.adapter.MqttClientAdapterRegistry;

import java.util.concurrent.CompletableFuture;

public final class DefaultMqttTemplate implements MqttTemplate {

    private final MqttClientAdapterRegistry adapterRegistry;

    public DefaultMqttTemplate(MqttClientAdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public void publish(String brokerId, String topic, Object payload) {
        publish(brokerId, topic, payload, 0, false);
    }

    @Override
    public void publish(String brokerId, String topic, Object payload, int qos, boolean retained) {
        MqttClientAdapter adapter = adapterRegistry.find(brokerId)
                .orElseThrow(() -> new IllegalArgumentException("No adapter registered for broker: " + brokerId));
        adapter.publish(topic, payload, qos, retained);
    }

    @Override
    public CompletableFuture<Void> publishAsync(String brokerId, String topic, Object payload) {
        return publishAsync(brokerId, topic, payload, 0, false);
    }

    @Override
    public CompletableFuture<Void> publishAsync(String brokerId, String topic, Object payload, int qos, boolean retained) {
        MqttClientAdapter adapter = adapterRegistry.find(brokerId)
                .orElseThrow(() -> new IllegalArgumentException("No adapter registered for broker: " + brokerId));
        return adapter.publishAsync(topic, payload, qos, retained);
    }
}