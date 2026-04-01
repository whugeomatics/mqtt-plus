package io.github.mqttplus.core;

import java.util.concurrent.CompletableFuture;

public interface MqttTemplate {

    void publish(String brokerId, String topic, Object payload);

    void publish(String brokerId, String topic, Object payload, int qos, boolean retained);

    CompletableFuture<Void> publishAsync(String brokerId, String topic, Object payload);

    CompletableFuture<Void> publishAsync(String brokerId, String topic, Object payload, int qos, boolean retained);
}