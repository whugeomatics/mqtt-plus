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
        MqttClientAdapter adapter = adapterRegistry.find(brokerId)
                .orElseThrow(() -> new IllegalArgumentException("No adapter registered for broker: " + brokerId));
        adapter.publish(topic, payload);
    }

    @Override
    public CompletableFuture<Void> publishAsync(String brokerId, String topic, Object payload) {
        MqttClientAdapter adapter = adapterRegistry.find(brokerId)
                .orElseThrow(() -> new IllegalArgumentException("No adapter registered for broker: " + brokerId));
        return adapter.publishAsync(topic, payload);
    }
}
