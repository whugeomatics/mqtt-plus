package io.github.mqttplus.core.router;

import io.github.mqttplus.core.model.MqttListenerDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MqttListenerRegistry {

    private final List<MqttListenerDefinition> definitions = new CopyOnWriteArrayList<>();
    private final MqttTopicMatcher topicMatcher = new MqttTopicMatcher();

    public void register(MqttListenerDefinition definition) {
        definitions.add(definition);
    }

    public List<MqttListenerDefinition> resolve(String brokerId, String topic) {
        List<MqttListenerDefinition> matches = new ArrayList<>();
        for (MqttListenerDefinition definition : definitions) {
            if (!(definition.getBroker().equals(brokerId) || definition.getBroker().equals("*"))) {
                continue;
            }
            for (String pattern : definition.getTopics()) {
                if (topicMatcher.matches(pattern, topic)) {
                    matches.add(definition);
                    break;
                }
            }
        }
        return List.copyOf(matches);
    }

    public List<MqttListenerDefinition> resolveByBroker(String brokerId) {
        List<MqttListenerDefinition> matches = new ArrayList<>();
        for (MqttListenerDefinition definition : definitions) {
            if (definition.getBroker().equals(brokerId) || definition.getBroker().equals("*")) {
                matches.add(definition);
            }
        }
        return List.copyOf(matches);
    }
}
