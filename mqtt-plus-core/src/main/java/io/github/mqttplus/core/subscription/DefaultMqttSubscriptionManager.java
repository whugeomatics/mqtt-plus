package io.github.mqttplus.core.subscription;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class DefaultMqttSubscriptionManager implements MqttSubscriptionManager {

    private final Map<String, Set<String>> subscriptionsByBroker = new ConcurrentHashMap<>();

    @Override
    public void addSubscription(String brokerId, String topic, int qos) {
        subscriptionsByBroker.computeIfAbsent(brokerId, ignored -> ConcurrentHashMap.newKeySet()).add(topic);
    }

    @Override
    public void removeSubscription(String brokerId, String topic) {
        Set<String> topics = subscriptionsByBroker.get(brokerId);
        if (topics != null) {
            topics.remove(topic);
        }
    }

    @Override
    public Set<String> getSubscriptions(String brokerId) {
        return Set.copyOf(subscriptionsByBroker.getOrDefault(brokerId, Set.of()));
    }
}
