package io.github.mqttplus.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MqttBrokerDefinitionTest {

    @Test
    void shouldBuildBrokerDefinitionWithDefaults() {
        MqttBrokerDefinition definition = MqttBrokerDefinition.builder()
                .brokerId("primary")
                .host("127.0.0.1")
                .clientId("demo-client")
                .build();

        assertEquals("primary", definition.getBrokerId());
        assertEquals("127.0.0.1", definition.getHost());
        assertEquals(1883, definition.getPort());
        assertEquals("demo-client", definition.getClientId());
        assertEquals(60, definition.getKeepAliveInterval());
        assertEquals(30, definition.getConnectionTimeout());
        assertTrue(definition.getInboundThreadPool().getCoreSize() > 0);
    }
}
