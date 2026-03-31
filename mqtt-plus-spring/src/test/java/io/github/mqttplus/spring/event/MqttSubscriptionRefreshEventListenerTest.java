package io.github.mqttplus.spring.event;

import io.github.mqttplus.core.subscription.MqttSubscriptionManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MqttSubscriptionRefreshEventListenerTest {

    @Test
    void shouldBridgeSubscribeAndUnsubscribeEvents() {
        MqttSubscriptionManager subscriptionManager = Mockito.mock(MqttSubscriptionManager.class);
        MqttSubscriptionRefreshEventListener listener = new MqttSubscriptionRefreshEventListener(subscriptionManager);

        listener.onApplicationEvent(new MqttSubscriptionRefreshEvent(MqttSubscriptionRefreshEvent.Action.SUBSCRIBE, "primary", "devices/1/status", 1));
        listener.onApplicationEvent(new MqttSubscriptionRefreshEvent(MqttSubscriptionRefreshEvent.Action.UNSUBSCRIBE, "primary", "devices/1/status", 0));

        Mockito.verify(subscriptionManager).addSubscription("primary", "devices/1/status", 1);
        Mockito.verify(subscriptionManager).removeSubscription("primary", "devices/1/status");
    }
}
