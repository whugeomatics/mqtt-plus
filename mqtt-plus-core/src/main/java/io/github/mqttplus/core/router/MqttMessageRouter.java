package io.github.mqttplus.core.router;

import io.github.mqttplus.core.model.MqttHeaders;

public interface MqttMessageRouter {

    void route(String brokerId, String topic, byte[] payload, MqttHeaders headers);
}
