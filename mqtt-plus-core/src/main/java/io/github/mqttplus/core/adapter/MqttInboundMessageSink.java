package io.github.mqttplus.core.adapter;

import io.github.mqttplus.core.model.MqttHeaders;

public interface MqttInboundMessageSink {

    void onMessage(String brokerId, String topic, byte[] payload, MqttHeaders headers);
}
