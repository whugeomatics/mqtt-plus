package io.github.mqttplus.test;

import io.github.mqttplus.core.model.MqttHeaders;
import io.github.mqttplus.core.router.MqttMessageRouter;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttTestTemplateTest {

    @Test
    void shouldRouteIncomingStringPayload() {
        RecordingRouter router = new RecordingRouter();
        MqttTestTemplate template = new MqttTestTemplate(router);

        template.simulateIncoming("primary", "devices/1/status", "online");

        assertEquals("primary", router.brokerId);
        assertEquals("devices/1/status", router.topic);
        assertArrayEquals("online".getBytes(StandardCharsets.UTF_8), router.payload);
        assertEquals(null, router.headers.get("missing"));
    }

    @Test
    void shouldRouteIncomingBinaryPayloadWithHeaders() {
        RecordingRouter router = new RecordingRouter();
        MqttTestTemplate template = new MqttTestTemplate(router);
        MqttHeaders headers = new MqttHeaders(Map.of("origin", "test"));

        template.simulateIncoming("primary", "devices/1/raw", new byte[]{1, 2, 3}, headers);

        assertEquals("test", router.headers.getAsString("origin"));
        assertArrayEquals(new byte[]{1, 2, 3}, router.payload);
    }

    private static final class RecordingRouter implements MqttMessageRouter {
        private String brokerId;
        private String topic;
        private byte[] payload;
        private MqttHeaders headers;

        @Override
        public void route(String brokerId, String topic, byte[] payload, MqttHeaders headers) {
            this.brokerId = brokerId;
            this.topic = topic;
            this.payload = payload;
            this.headers = headers;
        }
    }
}