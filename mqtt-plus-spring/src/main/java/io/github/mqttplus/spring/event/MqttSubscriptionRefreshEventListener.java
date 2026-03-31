package io.github.mqttplus.spring.event;

import io.github.mqttplus.core.subscription.MqttSubscriptionManager;
import org.springframework.context.event.EventListener;

public final class MqttSubscriptionRefreshEventListener {

    private final MqttSubscriptionManager subscriptionManager;

    public MqttSubscriptionRefreshEventListener(MqttSubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    @EventListener
    public void onApplicationEvent(MqttSubscriptionRefreshEvent event) {
        if (event.getAction() == MqttSubscriptionRefreshEvent.Action.SUBSCRIBE) {
            subscriptionManager.addSubscription(event.getBrokerId(), event.getTopic(), event.getQos());
            return;
        }
        subscriptionManager.removeSubscription(event.getBrokerId(), event.getTopic());
    }
}