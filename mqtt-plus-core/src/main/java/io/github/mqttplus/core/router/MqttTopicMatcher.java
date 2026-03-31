package io.github.mqttplus.core.router;

public final class MqttTopicMatcher {

    public boolean matches(String subscription, String topic) {
        if (subscription == null || topic == null || subscription.isBlank() || topic.isBlank()) {
            return false;
        }
        if (topic.startsWith("$") && !subscription.startsWith("$")) {
            return false;
        }

        String[] subscriptionLevels = subscription.split("/", -1);
        String[] topicLevels = topic.split("/", -1);

        int s = 0;
        int t = 0;
        while (s < subscriptionLevels.length && t < topicLevels.length) {
            String subscriptionLevel = subscriptionLevels[s];
            if ("#".equals(subscriptionLevel)) {
                return s == subscriptionLevels.length - 1;
            }
            if (!"+".equals(subscriptionLevel) && !subscriptionLevel.equals(topicLevels[t])) {
                return false;
            }
            s++;
            t++;
        }
        if (s == subscriptionLevels.length && t == topicLevels.length) {
            return true;
        }
        return s == subscriptionLevels.length - 1 && "#".equals(subscriptionLevels[s]);
    }
}
