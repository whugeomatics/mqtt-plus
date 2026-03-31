package io.github.mqttplus.samples.dynamicsubscription;

import io.github.mqttplus.core.annotation.MqttListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DynamicSubscriptionListener {

    private static final Logger log = LoggerFactory.getLogger(DynamicSubscriptionListener.class);

    @MqttListener(broker = "primary", topics = "samples/dynamic/default", payloadType = String.class)
    public void onDefaultTopic(String payload) {
        log.info("Received dynamic sample payload: {}", payload);
    }
}