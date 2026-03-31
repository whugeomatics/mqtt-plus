package io.github.mqttplus.core.model;

import java.util.Arrays;
import java.util.Objects;

public final class MqttContext {

    private final String brokerId;
    private final String topic;
    private final byte[] payload;
    private final MqttHeaders headers;

    public MqttContext(String brokerId, String topic, byte[] payload, MqttHeaders headers) {
        this.brokerId = requireText(brokerId, "brokerId");
        this.topic = requireText(topic, "topic");
        this.payload = Objects.requireNonNull(payload, "payload must not be null").clone();
        this.headers = Objects.requireNonNull(headers, "headers must not be null");
    }

    public String getBrokerId() {
        return brokerId;
    }

    public String getTopic() {
        return topic;
    }

    public byte[] getPayload() {
        return payload.clone();
    }

    public MqttHeaders getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "MqttContext{" +
                "brokerId='" + brokerId + '\'' +
                ", topic='" + topic + '\'' +
                ", payloadSize=" + payload.length +
                ", headers=" + headers.asMap() +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MqttContext that)) {
            return false;
        }
        return brokerId.equals(that.brokerId)
                && topic.equals(that.topic)
                && Arrays.equals(payload, that.payload)
                && headers.asMap().equals(that.headers.asMap());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(brokerId, topic, headers.asMap());
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
