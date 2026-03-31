package io.github.mqttplus.test;

import io.github.mqttplus.core.model.MqttHeaders;
import io.github.mqttplus.core.router.MqttMessageRouter;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class MqttTestTemplate {

    private final MqttMessageRouter messageRouter;

    public MqttTestTemplate(MqttMessageRouter messageRouter) {
        this.messageRouter = Objects.requireNonNull(messageRouter, "messageRouter must not be null");
    }

    public void simulateIncoming(String brokerId, String topic, String payload) {
        simulateIncoming(brokerId, topic, payload.getBytes(StandardCharsets.UTF_8), MqttHeaders.empty());
    }

    public void simulateIncoming(String brokerId, String topic, byte[] payload) {
        simulateIncoming(brokerId, topic, payload, MqttHeaders.empty());
    }

    public void simulateIncoming(String brokerId, String topic, byte[] payload, MqttHeaders headers) {
        Objects.requireNonNull(brokerId, "brokerId must not be null");
        Objects.requireNonNull(topic, "topic must not be null");
        Objects.requireNonNull(payload, "payload must not be null");
        Objects.requireNonNull(headers, "headers must not be null");
        messageRouter.route(brokerId, topic, payload.clone(), headers);
    }
}