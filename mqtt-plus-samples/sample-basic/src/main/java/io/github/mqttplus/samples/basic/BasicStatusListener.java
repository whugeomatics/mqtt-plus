package io.github.mqttplus.samples.basic;

import io.github.mqttplus.core.annotation.MqttListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BasicStatusListener {

    private static final Logger log = LoggerFactory.getLogger(BasicStatusListener.class);

    @MqttListener(broker = "primary", topics = "samples/basic/status", payloadType = String.class)
    public void onStatus(String payload) {
        log.info("Received basic sample payload: {}", payload);
    }
}