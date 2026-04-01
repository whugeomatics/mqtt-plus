package io.github.mqttplus.core.adapter;

import io.github.mqttplus.core.model.MqttBrokerDefinition;

import java.util.concurrent.CompletableFuture;

public interface MqttClientAdapter {

    String getBrokerId();

    MqttBrokerDefinition getBrokerDefinition();

    void connect();

    void disconnect();

    void subscribe(String topic, int qos);

    void unsubscribe(String topic);

    void publish(String topic, Object payload);

    void publish(String topic, Object payload, int qos, boolean retained);

    CompletableFuture<Void> publishAsync(String topic, Object payload);

    CompletableFuture<Void> publishAsync(String topic, Object payload, int qos, boolean retained);

    boolean supportsManualAck();

    void addConnectionListener(MqttConnectionListener listener);
}