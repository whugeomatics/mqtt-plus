package io.github.mqttplus.paho;

import io.github.mqttplus.core.adapter.MqttClientAdapter;
import io.github.mqttplus.core.model.MqttBrokerDefinition;
import io.github.mqttplus.core.model.ThreadPoolConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PahoMqttClientAdapterFactoryTest {

    @Test
    void shouldCreatePahoAdapterFromBrokerDefinition() {
        PahoMqttClientAdapterFactory factory = new PahoMqttClientAdapterFactory();
        MqttBrokerDefinition definition = MqttBrokerDefinition.builder()
                .brokerId("primary")
                .host("127.0.0.1")
                .port(1883)
                .clientId("paho-test")
                .inboundThreadPool(ThreadPoolConfig.builder().build())
                .build();

        MqttClientAdapter adapter = factory.create(definition, (brokerId, topic, payload, headers) -> {
        });

        assertEquals(PahoMqttClientAdapterFactory.SUPPORTED_VERSION, factory.supportedVersion());
        assertInstanceOf(PahoMqttClientAdapter.class, adapter);
        assertEquals("primary", adapter.getBrokerId());
        assertFalse(adapter.supportsManualAck());
    }
}
