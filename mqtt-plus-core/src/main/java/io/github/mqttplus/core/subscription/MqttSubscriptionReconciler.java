package io.github.mqttplus.core.subscription;

import io.github.mqttplus.core.adapter.MqttClientAdapter;
import io.github.mqttplus.core.adapter.MqttClientAdapterRegistry;
import io.github.mqttplus.core.adapter.MqttConnectionListener;
import io.github.mqttplus.core.model.MqttListenerDefinition;
import io.github.mqttplus.core.router.MqttListenerRegistry;

public final class MqttSubscriptionReconciler implements MqttConnectionListener {

    private final MqttClientAdapterRegistry adapterRegistry;
    private final MqttListenerRegistry listenerRegistry;
    private final MqttSubscriptionManager subscriptionManager;

    public MqttSubscriptionReconciler(MqttClientAdapterRegistry adapterRegistry,
                                      MqttListenerRegistry listenerRegistry,
                                      MqttSubscriptionManager subscriptionManager) {
        this.adapterRegistry = adapterRegistry;
        this.listenerRegistry = listenerRegistry;
        this.subscriptionManager = subscriptionManager;
    }

    @Override
    public void onConnected(String brokerId) {
        MqttClientAdapter adapter = adapterRegistry.find(brokerId)
                .orElseThrow(() -> new IllegalStateException("No adapter registered for broker: " + brokerId));
        for (MqttListenerDefinition definition : listenerRegistry.resolveByBroker(brokerId)) {
            for (String topic : definition.getTopics()) {
                adapter.subscribe(topic, definition.getQos());
            }
        }
        for (String topic : subscriptionManager.getSubscriptions(brokerId)) {
            adapter.subscribe(topic, 0);
        }
    }

    @Override
    public void onConnectionLost(String brokerId, Throwable cause) {
    }

    @Override
    public void onDisconnected(String brokerId) {
    }
}
