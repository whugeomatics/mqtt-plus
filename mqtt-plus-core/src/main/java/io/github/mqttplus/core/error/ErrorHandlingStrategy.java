package io.github.mqttplus.core.error;

import io.github.mqttplus.core.model.MqttContext;
import io.github.mqttplus.core.model.MqttListenerDefinition;

public interface ErrorHandlingStrategy {

    ErrorAction onError(MqttListenerDefinition definition, MqttContext context, Throwable error);
}
