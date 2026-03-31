package io.github.mqttplus.samples.multibroker;

import io.github.mqttplus.core.annotation.MqttListener;
import io.github.mqttplus.core.annotation.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SharedListener {

    private static final Logger log = LoggerFactory.getLogger(SharedListener.class);

    @MqttListener(broker = "*", topics = {"samples/public/status", "samples/private/status"}, payloadType = String.class)
    public void onMessage(String payload, @MqttTopic String topic) {
        log.info("Received multi-broker payload on {}: {}", topic, payload);
    }
}