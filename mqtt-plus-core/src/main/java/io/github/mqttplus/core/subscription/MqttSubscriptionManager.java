package io.github.mqttplus.core.subscription;

import java.util.Set;

public interface MqttSubscriptionManager {

    void addSubscription(String brokerId, String topic, int qos);

    void removeSubscription(String brokerId, String topic);

    Set<String> getSubscriptions(String brokerId);
}
