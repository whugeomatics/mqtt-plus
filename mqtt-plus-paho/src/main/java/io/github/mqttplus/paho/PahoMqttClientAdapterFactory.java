package io.github.mqttplus.paho;

import io.github.mqttplus.core.adapter.MqttClientAdapter;
import io.github.mqttplus.core.adapter.MqttClientAdapterFactory;
import io.github.mqttplus.core.adapter.MqttInboundMessageSink;
import io.github.mqttplus.core.model.MqttBrokerDefinition;
import org.eclipse.paho.client.mqttv3.MqttException;

public final class PahoMqttClientAdapterFactory implements MqttClientAdapterFactory {

    public static final String SUPPORTED_VERSION = "3.1.1";

    @Override
    public String supportedVersion() {
        return SUPPORTED_VERSION;
    }

    @Override
    public MqttClientAdapter create(MqttBrokerDefinition brokerDefinition, MqttInboundMessageSink inboundMessageSink) {
        try {
            return new PahoMqttClientAdapter(brokerDefinition, inboundMessageSink);
        } catch (MqttException ex) {
            throw new IllegalStateException("Failed to create Paho adapter", ex);
        }
    }
}
