package io.github.mqttplus.samples.dynamicsubscription;

import io.github.mqttplus.spring.event.MqttSubscriptionRefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
public class DynamicSubscriptionController {

    private final ApplicationEventPublisher publisher;

    public DynamicSubscriptionController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String topic) {
        publisher.publishEvent(new MqttSubscriptionRefreshEvent(
                MqttSubscriptionRefreshEvent.Action.SUBSCRIBE,
                "primary",
                topic,
                0));
        return "subscribed:" + topic;
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestParam String topic) {
        publisher.publishEvent(new MqttSubscriptionRefreshEvent(
                MqttSubscriptionRefreshEvent.Action.UNSUBSCRIBE,
                "primary",
                topic,
                0));
        return "unsubscribed:" + topic;
    }
}