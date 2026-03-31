package io.github.mqttplus.core.reconnect;

import java.time.Duration;

public interface ReconnectStrategy {

    Duration nextDelay(int attempt);
}
