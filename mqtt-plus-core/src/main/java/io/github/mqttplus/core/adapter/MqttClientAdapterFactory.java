package io.github.mqttplus.core.adapter;

import io.github.mqttplus.core.model.MqttBrokerDefinition;

public interface MqttClientAdapterFactory {

    String supportedVersion();

    MqttClientAdapter create(MqttBrokerDefinition brokerDefinition, MqttInboundMessageSink inboundMessageSink);
}
